package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.authorizationservice.exceptions.AppError;
import ru.gb.authorizationservice.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Методы работы с базой пользователей")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Запрос на удаление пользователя",
            responses = {
                    @ApiResponse(
                            description = "Пользователь успешно удален", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/delete")
    public void deleteUserById(@RequestParam Long deleteUserId, @RequestHeader String userId) {
        //  deleteUserId - какого пользователя удаляем
        //  userId - кто послал запрос на удаление пользователя
        userService.safeDeleteById(deleteUserId, userId);
    }


}
