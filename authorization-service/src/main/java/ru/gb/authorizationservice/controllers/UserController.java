package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.UserDto;
import ru.gb.authorizationservice.converters.UserConverter;
import ru.gb.authorizationservice.exceptions.AppError;
import ru.gb.authorizationservice.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Методы работы с базой пользователей")
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;


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

    @Operation(
            summary = "Вывод всех не удаленных пользователей на странице админа"
    )
    @GetMapping("list_all_not_deleted")
    public List<UserDto> listAllNotDeleted() {
        return userService.findAllNotDeleted().stream().map(userConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Вывод всех пользователей на странице админа, включая удаленных"
    )
    @GetMapping("list_all")
    public List<UserDto> listAll() {
        return userService.findAll().stream().map(userConverter::entityToDto).toList();
    }


}
