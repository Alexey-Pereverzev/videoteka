package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.RoleChangeDto;

import ru.gb.api.dtos.dto.StringResponse;
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
                            description = "Пользователь не найден", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RoleChangeDto roleChangeDto,
                                 @RequestHeader String userId) {
        //  changeUserId - какого пользователя изменяем
        //  userId - кто послал запрос на изменение пользователя
        String result = userService.setRoleToUser(roleChangeDto.getChangeUserId(), userId, roleChangeDto.getRole());
        if (result.equals("")) {
            return ResponseEntity.ok(new StringResponse("Роль успешно изменена"));
        } else {
            return new ResponseEntity<>(new AppError("INPUT_DATA_ERROR", result), HttpStatus.BAD_REQUEST);
        }
    }


}