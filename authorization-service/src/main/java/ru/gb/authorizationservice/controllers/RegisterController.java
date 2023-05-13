package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.RegisterUserDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.AppError;
import ru.gb.authorizationservice.services.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reg")
@Tag(name = "Регистрация", description = "Метод регистрации пользователя")
public class RegisterController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Запрос на регистрацию пользователя",
            responses = {
                    @ApiResponse(
                            description = "Заказ успешно создан", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка (пользователь уже есть в системе, не совпадают пароли или ошибка ввода данных)",
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto registerUserDto) {

        Optional<User> user = userService.findByUsername(registerUserDto.getUsername());

        if (user.isPresent()) {
            if (!user.get().isDeleted()) {
                return new ResponseEntity<>(new AppError("USER_ALREADY_EXISTS",
                        "Такой пользователь уже есть в системе"), HttpStatus.BAD_REQUEST);
            } else {    // пользователь есть в системе, но был удален - восстанавливаем
                String bcryptCachedPassword = passwordEncoder.encode(registerUserDto.getPassword());
                String tryToRestore = userService.restoreUser(registerUserDto, bcryptCachedPassword, user.get());
                if (!tryToRestore.equals("")) {
                    return new ResponseEntity<>(new AppError("INPUT_DATA_ERROR",
                            tryToRestore), HttpStatus.BAD_REQUEST);
                } else {
                    return ResponseEntity.ok(new StringResponse("Пользователь с именем "
                            + registerUserDto.getUsername() + " успешно создан"));
                }

            }
        } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError("NOT_MATCHING_PASSWORDS",
                    "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        } else {
            String bcryptCachedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            String tryToCreate = userService.createNewUser(registerUserDto, bcryptCachedPassword);
            if (!tryToCreate.equals("")) {
                return new ResponseEntity<>(new AppError("INPUT_DATA_ERROR",
                        tryToCreate), HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.ok(new StringResponse("Пользователь с именем "
                        + registerUserDto.getUsername() + " успешно создан"));
            }
        }
    }
}
