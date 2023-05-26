package ru.gb.api.dtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ответа на запрос авторизации")
public class JwtResponse {
    @Schema(description = "Токен", required = true,
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTY3MzgzODkxOSwiaWF0IjoxNjczODAyOTE5fQ.Y3gN-8PxmClJuxX71-RM_Xffwfg30UqA9NU_tKgceUI")
    private String token;

    @Schema(description = "Роль пользователя", required = true, example = "'ROLE_USER'")
    private String role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
