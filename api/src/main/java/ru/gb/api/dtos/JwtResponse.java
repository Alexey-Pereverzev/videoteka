package ru.gb.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Модель ответа на запрос авторизации")
public class JwtResponse {
    @Schema(description = "Токен", required = true,
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTY3MzgzODkxOSwiaWF0IjoxNjczODAyOTE5fQ.Y3gN-8PxmClJuxX71-RM_Xffwfg30UqA9NU_tKgceUI")
    private String token;

    @Schema(description = "Список ролей", required = true, example = "['ROLE_USER']")
    private List<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}
