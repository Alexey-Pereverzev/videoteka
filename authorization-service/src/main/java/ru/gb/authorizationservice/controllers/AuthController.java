package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.JwtRequest;
import ru.gb.api.dtos.JwtResponse;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.AppError;
import ru.gb.authorizationservice.services.UserService;
import ru.gb.authorizationservice.utils.JwtTokenUtil;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Методы сервиса аутентификации")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Запрос на создание токена (аутентификацию)",
            responses = {
                    @ApiResponse(
                            description = "Токен успешно создан", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(
                            description = "Некорректный логин/пароль", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Недопустимый символ $ в логине", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        String username = authRequest.getUsername();
        if (userService.IsDollarSignPresent(username)) {        //
            return new ResponseEntity<>
                    (new AppError("BAD_REQUEST", "Символ '$' в имени пользователя недопустим"),
                            HttpStatus.BAD_REQUEST);
        }

        Optional<User> userByUsername = userService.findNotDeletedByUsername(username);
        // проверяем, есть ли не удаленный пользователь с таким именем

        if (userByUsername.isEmpty()) {         //  если нет, кидаем ошибку
            return new ResponseEntity<>(
                    new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"),
                    HttpStatus.NOT_FOUND);
        }

        try {           //  если есть такой пользователь
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"),
                    HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userService.getRoles(authRequest.getUsername())));
    }

}
