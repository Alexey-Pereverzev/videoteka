package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.RegisterUserDto;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final InputValidationService validationService = new InputValidationService();


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
                .User(user.getUsername(), user.getPassword(),
                Collections.singleton(mapRoleToAuthority(user.getRole())));     // Singleton потому что роль может быть только 1
    }

    @Transactional
    public String getRole(String username) {
        User user = findNotDeletedByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return user.getRole().getTitle();
    }

    private SimpleGrantedAuthority mapRoleToAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getTitle());
    }

    @Transactional
    public String createNewUser(RegisterUserDto registerUserDto, String encryptedPassword) {

        User user = new User();
        user.setRole(roleService.getUserRole());


        String username = registerUserDto.getUsername();
        String email = registerUserDto.getEmail();
        String firstName = registerUserDto.getFirstName();
        String lastName = registerUserDto.getLastName();
        String phoneNumber = registerUserDto.getPhoneNumber();
        String address = registerUserDto.getAddress();
        String validationMessage = "";


        validationMessage = validationService.acceptableLogin(username);
        if (validationMessage.equals("")) {
            user.setUsername(username);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptableLogin(username);
        if (validationMessage.equals("")) {
            user.setUsername(username);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptablePassword(registerUserDto.getPassword());
                //  здесь берем пароль из ДТО, т.к. валидность проверяем у незашифрованного пароля, а в базу сохраняем
                //  encryptedPassword - зашифрованный пароль
        if (validationMessage.equals("")) {
            user.setPassword(encryptedPassword);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptableEmail(email);
        if (validationMessage.equals("")) {
            user.setEmail(email);
        } else {
            return validationMessage;
        }


        if (!firstName.isEmpty()&&!firstName.isBlank()) {
            validationMessage = validationService.acceptableFirstName(firstName);
            if (validationMessage.equals("")) {
                user.setFirstName(firstName);
            } else {
                return validationMessage;
            }
        }

        if (!lastName.isEmpty()&&!lastName.isBlank()) {
            validationMessage = validationService.acceptableLastName(lastName);
            if (validationMessage.equals("")) {
                user.setLastName(lastName);
            } else {
                return validationMessage;
            }
        }

        if (!phoneNumber.isEmpty()&&!phoneNumber.isBlank()) {
            if (!validationService.acceptablePhoneNumber(phoneNumber)) {
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




//    public boolean IsDollarSignPresent(String username) {
//        //  записываем в конец имени удаленного пользователя знак "$", чтобы избежать ошибки бина
//        //  AuthenticationManager при аутентификации (имя "живого" пользователя в базе всегда уникальное)
//        return username.indexOf('$') >= 0;
//    }
//
//    @Transactional
//    public void deleteById(Long id) {
//        Optional<User> u = userRepository.findById(id);
//        if (u.isPresent()) {
//            if (!u.get().isDeleted()) {
//                u.get().setUsername(u.get().getUsername().concat("$"));
//                // удаляемому пользователю приписываем знак $ в конец
//            } else {
//                throw new ResourceNotFoundException("Пользователь с id: " +id + " не найден или удален");
//            }
//        } else {
//            throw new ResourceNotFoundException("Пользователь с id: " +id + " не найден или удален");
//        }
//    }
//
//    @Transactional
//    public void deleteByUsername(String username) {
//        Optional<User> u = findNotDeletedByUsername(username);
//        if (u.isPresent()) {
//            u.get().setUsername(username.concat("$"));
//                // удаляемому пользователю приписываем знак $ в конец
//        } else {
//            throw new ResourceNotFoundException("Пользователь с именем: " + username + " не найден или удален");
//        }
//    }



}
