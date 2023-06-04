package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.api.dtos.dto.UserDto;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.authorizationservice.converters.UserConverter;
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
                            description = "Пользователь успешно удален", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Админ не найден в базе по id", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/delete")
    public StringResponse deleteUserById(@RequestParam Long deleteUserId, @RequestHeader String userId) {
        //  deleteUserId - какого пользователя удаляем
        //  userId - кто послал запрос на удаление пользователя
        userService.safeDeleteById(deleteUserId, userId);
        return new StringResponse("Пользователь успешно удален");
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

    @Operation(
            summary = "Имя и мэйл по id",
            description = "Вывод имени и емэйла пользователя по id",
            responses = {
                    @ApiResponse(
                            description = "Пользователь найден, вернули результат", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("get_name_and_email_by_id")
    public UserNameMailDto getNameAndEmailById(@RequestParam Long id) {
        return userConverter.entityToNameMailDto(userService.findNameEmailById(id));
    }

    @Operation(
            summary = "Фамилия и имя по id",
            responses = {
            @ApiResponse(
                    description = "Имя и фамилия", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = StringResponse.class))
            ),
            @ApiResponse(
                    description = "Пользователь не найден", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = AppError.class))
            )
    }
    )
    @GetMapping("get_fullname_by_id")
    public StringResponse fullNameById(@RequestParam Long userId) {
            return userService.fullNameById(userId);
    }


}
