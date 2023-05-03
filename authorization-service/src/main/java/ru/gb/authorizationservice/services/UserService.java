package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.RegisterUserDto;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private static final PhoneNumberService phoneService = new PhoneNumberService();



    public List<User> findByUsername(String username) {     //  найти все версии пользователя, включая удаленные
        return userRepository.findByUsername(username);
    }

    public Optional<User> findNotDeletedByUsername(String username) {   // найти не удаленного пользователя с нужным именем
        List<User> found = findByUsername(username);
        Optional<User> result = Optional.empty();
        for (User user : found) {
            if (!user.isDeleted()) {
                result = Optional.of(user);
                break;
            }
        }
        return result;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findNotDeletedByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Transactional
    public List<String> getRoles(String username) {
        User user = findNotDeletedByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return user.getRoles().stream().map(Role::getTitle).toList();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toList());
    }

    public boolean IsDollarSignPresent(String username) {
        //  записываем в конец имени удаленного пользователя знак "$", чтобы избежать ошибки бина
        //  AuthenticationManager при аутентификации (имя "живого" пользователя в базе всегда уникальное)
        return username.indexOf('$') >= 0;
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            if (!u.get().isDeleted()) {
                u.get().setUsername(u.get().getUsername().concat("$"));
                // удаляемому пользователю приписываем знак $ в конец
            } else {
                throw new ResourceNotFoundException("Пользователь с id: " +id + " не найден или удален");
            }
        } else {
            throw new ResourceNotFoundException("Пользователь с id: " +id + " не найден или удален");
        }
    }

    @Transactional
    public void deleteByUsername(String username) {
        Optional<User> u = findNotDeletedByUsername(username);
        if (u.isPresent()) {
            u.get().setUsername(username.concat("$"));
                // удаляемому пользователю приписываем знак $ в конец
        } else {
            throw new ResourceNotFoundException("Пользователь с именем: " + username + " не найден или удален");
        }
    }

    @Transactional
    public String createNewUser(RegisterUserDto registerUserDto, String password) {
        User user = User.Builder.newBuilder()
                .withUsername(registerUserDto.getUsername())
                .withPassword(password)
                .withEmail(registerUserDto.getEmail())
                .withRoles(List.of(roleService.getUserRole()))
                .build();

        String firstName = registerUserDto.getFirstName();
        String lastName = registerUserDto.getLastName();
        String phoneNumber = registerUserDto.getPhoneNumber();
        String address = registerUserDto.getAddress();

        if (!firstName.isEmpty()&&!firstName.isBlank()) {
            user.setFirstName(firstName);
        }

        if (!lastName.isEmpty()&&!lastName.isBlank()) {
            user.setLastName(lastName);
        }

        if (!phoneNumber.isEmpty()&&!phoneNumber.isBlank()) {
            if (!phoneService.acceptablePhoneNumber(phoneNumber)) {
                return "Некорректный номер телефона";
            }
            user.setPhoneNumber(phoneNumber);
        }

        if (!address.isEmpty()&&!address.isBlank()) {
            user.setAddress(address);
        }

        userRepository.save(user);
        return "";
    }
}
