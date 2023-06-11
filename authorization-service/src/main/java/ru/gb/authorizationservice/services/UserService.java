package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.dto.RegisterUserDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.entities.PasswordChangeAttempt;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.InputDataErrorException;
import ru.gb.authorizationservice.exceptions.NotDeletedUserException;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.integrations.MailServiceIntegration;
import ru.gb.authorizationservice.repositories.PasswordChangeAttemptRepository;
import ru.gb.authorizationservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordChangeAttemptRepository attemptRepository;
    private final RoleService roleService;
    private final InputValidationService validationService = new InputValidationService();
    private final MailServiceIntegration mailServiceIntegration;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public StringResponse createNewUser(RegisterUserDto registerUserDto) {
        if (registerUserDto.getPassword()==null) {
            throw new InputDataErrorException("Пароль не может быть пустым");
        } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            throw new InputDataErrorException("Пароли не совпадают");
        } else {
            User user = new User();
            String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());

            String validationMessage = validateAndSaveFields(registerUserDto, encryptedPassword, user);
            if (validationMessage != null) {
                throw new InputDataErrorException(validationMessage);
            }

            userRepository.save(user);
            return new StringResponse("Пользователь с именем "
                    + registerUserDto.getUsername() + " успешно создан");
        }
    }

    @Transactional
    public StringResponse restoreUser(RegisterUserDto registerUserDto, User user) {
    // восстанавливаем пользователя, если он есть в системе со статусом isDeleted = true
        if (!user.isDeleted()) {
            throw new NotDeletedUserException("Такой пользователь уже есть в системе");
        } else {
            if (registerUserDto.getPassword() == null) {
                throw new InputDataErrorException("Пароль не может быть пустым");
            } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                throw new InputDataErrorException("Пароли не совпадают");
            } else {
                String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());
                user.setDeleted(false);
                user.setDeletedBy(null);
                user.setDeletedWhen(null);

                String validationMessage = validateAndSaveFields(registerUserDto, encryptedPassword, user);
                if (validationMessage != null) {
                    throw new InputDataErrorException(validationMessage);
                }

                userRepository.save(user);
                return new StringResponse("Пользователь с именем "
                        + registerUserDto.getUsername() + " успешно восстановлен");
            }
        }

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
    public void setRoleToUser(Long changeUserId, String adminId, String role) {
        User user = userRepository.findById(changeUserId).orElseThrow
                (() -> new ResourceNotFoundException("Пользователь с id: " + changeUserId + " не найден"));
        user.setRole(roleService.getRoleByName(role).orElseThrow
                (() -> new ResourceNotFoundException("Роль " + role + " в базе не найдена")));
        User admin = findById(adminId).orElseThrow
                (() -> new ResourceNotFoundException("Aдмин с id: " + adminId + " не найден"));
        if (!admin.getRole().getTitle().equals("ROLE_ADMIN")) {
            throw new ResourceNotFoundException("Aдмин с id: " + adminId + " не найден");
        }
        user.setUpdateBy(findById(adminId).orElseThrow
                (() -> new ResourceNotFoundException("Пользователь с id: " + adminId + " не найден"))
                .getUsername());
        userRepository.save(user);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(Long.valueOf(userId));
    }

    @Transactional
    public void safeDeleteById(Long deleteUserId, String adminId) {
        User u = userRepository.findById(deleteUserId).orElseThrow(() -> new ResourceNotFoundException
                ("Пользователь с id: " + deleteUserId + " не найден или удален"));

        if (!u.isDeleted()) {
            u.setDeleted(true);
            u.setDeletedBy(findById(adminId).orElseThrow(() -> new InputDataErrorException
                    ("Админ с id: " + adminId + " не найден")).getUsername());
            u.setDeletedWhen(LocalDateTime.now());
            userRepository.save(u);

        } else {
            throw new ResourceNotFoundException
                    ("Пользователь с id: " + deleteUserId + " не найден или удален");
        }
    }

    public List<User> findAllNotDeleted() {
        return userRepository.findAllNotDeleted();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public StringResponse fullNameById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow
                (() -> new ResourceNotFoundException("Нет такого пользователя"));
        return new StringResponse(user.getFirstName().concat(" ").concat(user.getLastName()));
    }

    public User findNameEmailById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Польователь с id="+id+" не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id="+id+" был удален");
        }
        return user;
    }

    public void updatePassword(String userId, String email) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() ->
                new ResourceNotFoundException("Польователь с id=" + userId + " не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id=" + userId + " не найден");
        }
        if (!user.getEmail().equals(email)) {
            throw new InputDataErrorException("Некорректный емэйл");
        }
        String code = mailServiceIntegration.composeVerificationLetter(user.getFirstName(), email);


        PasswordChangeAttempt attempt = new PasswordChangeAttempt();


    }
}