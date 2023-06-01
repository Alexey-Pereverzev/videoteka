package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.JwtRequest;
import ru.gb.api.dtos.dto.JwtResponse;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.services.UserService;
import ru.gb.authorizationservice.utils.JwtTokenUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
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
                            description = "Некорректный логин/пароль", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Пользователя нет в БД (не зарегистрирован)", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
            }
    )
    @PostMapping("/authenticate")
    public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String username = authRequest.getUsername();

        User userByUsername = userService.findNotDeletedByUsername(username);
        // проверяем, есть ли не удаленный пользователь с таким именем, если нет = статус 404

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword()));
        // авторизация, если неуспешно - выдаем 400

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails,userByUsername.getId());
        return new JwtResponse(token, userService.getRole(authRequest.getUsername()));
    }


}