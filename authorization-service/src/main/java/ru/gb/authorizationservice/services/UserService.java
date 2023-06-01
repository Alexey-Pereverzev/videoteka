package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.dto.RegisterUserDto;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final InputValidationService validationService = new InputValidationService();


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findNotDeletedByUsername(String username) {   // найти не удаленного пользователя с нужным именем
        Optional<User> found = findByUsername(username);
        if (found.isPresent() && !found.get().isDeleted()) {
            return found;
        } else {
            return Optional.empty();
        }
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

        String validationMessage = validateAndSaveFields(registerUserDto, encryptedPassword, user);
        if (validationMessage != null) return validationMessage;

        userRepository.save(user);
        return "";
    }

    @Transactional
    public String restoreUser(RegisterUserDto registerUserDto, String encryptedPassword, User user) {
        user.setDeleted(false);
        user.setDeletedBy(null);
        user.setDeletedWhen(null);

        String validationMessage = validateAndSaveFields(registerUserDto, encryptedPassword, user);
        if (validationMessage != null) return validationMessage;

        userRepository.save(user);
        return "";
    }

    private String validateAndSaveFields(RegisterUserDto registerUserDto, String encryptedPassword, User user) {
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


        validationMessage = validationService.acceptableFirstName(firstName);
        if (validationMessage.equals("")) {
            user.setFirstName(firstName);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptableLastName(lastName);
        if (validationMessage.equals("")) {
            user.setLastName(lastName);
        } else {
            return validationMessage;
        }


        if (phoneNumber==null || phoneNumber.isBlank()) {
            user.setPhoneNumber(null);
        } else {
            if (!validationService.acceptablePhoneNumber(phoneNumber)) {
                return "Некорректный номер телефона";
            }
            user.setPhoneNumber(phoneNumber);
        }

        if (address==null || address.isBlank()) {
            user.setAddress(null);
        } else {
            user.setAddress(address);
        }

        return null;    //  валидация прошла успешно
    }


    @Transactional
    public String setRoleToUser(Long changeUserId, String adminId, String role) {
        Optional<User> changeUser = userRepository.findById(changeUserId);
        if (changeUser.isPresent()) {
            User user = changeUser.get();
            Optional<Role> newRole = roleService.getRoleByName(role);
            if (newRole.isPresent()) {
                user.setRole(newRole.get());
            } else {
                return "Роль " + role + " в базе не найдена";
            }
            Optional<User> changer = findById(adminId);
            if (changer.isPresent()) {
                user.setUpdateBy(changer.get().getUsername());
            } else {
                return "Пользователь с id: " + adminId + " не найден";
            }
            userRepository.save(user);
            return "";
        } else {
            return "Пользователь с id: " + changeUserId + " не найден";
        }
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(Long.valueOf(userId));
    }

    @Transactional
    public String safeDeleteById(Long deleteUserId, String adminId) {
        Optional<User> u = userRepository.findById(deleteUserId);
        if (u.isPresent()) {
            if (!u.get().isDeleted()) {
                u.get().setDeleted(true);
                Optional<User> admin = findById(adminId);
                if (admin.isPresent()) {
                    u.get().setDeletedBy(admin.get().getUsername());
                } else {
                    return "Пользователь с id: " + adminId + " не найден";
                }
                u.get().setDeletedWhen(LocalDateTime.now());
                userRepository.save(u.get());
                return "";
            } else {
                return "Пользователь с id: " + deleteUserId + " не найден или удален";
            }
        } else {
            return "Пользователь с id: " + deleteUserId + " не найден или удален";
        }
    }

    public List<User> findAllNotDeleted() {
        return userRepository.findAllNotDeleted();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String fullNameById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && !user.get().isDeleted()) {
            return user.get().getFirstName().concat(" ").concat(user.get().getLastName());
        } else {
            return "";
        }
    }

    public User findNameEmailById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Польователь с id="+id+" не найден"));
    }
}