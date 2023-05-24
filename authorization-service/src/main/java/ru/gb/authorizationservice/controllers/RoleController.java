package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.RoleChangeDto;
import ru.gb.authorizationservice.exceptions.AppError;
import ru.gb.authorizationservice.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@Tag(name = "Роли", description = "Методы работы с ролями пользователя")
public class RoleController {

    private final UserService userService;

    @Operation(
            summary = "Запрос на изменение роли пользователя",
            responses = {
                    @ApiResponse(
                            description = "Роль успешно изменена", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody RoleChangeDto roleChangeDto,
                       @RequestHeader String userId) {
        //  changeUserId - какого пользователя изменяем
        //  userId - кто послал запрос на изменение пользователя
        userService.setRoleToUser(roleChangeDto.getChangeUserId(), userId, roleChangeDto.getRole());
    }


}