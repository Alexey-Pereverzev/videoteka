package ru.gb.cabinetorderservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.api.dtos.exceptions.ResourceNotFoundException;
import ru.gb.api.dtos.order.OrderDto;
import ru.gb.cabinetorderservice.converters.OrderConverter;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.exceptions.AppError;
import ru.gb.cabinetorderservice.services.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы" , description = "Методы работы с заказами")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Создание заказа",
            description = "Созадение заказа "
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String userId) {

        orderService.createOrder(userId);
    }

    @Operation(
            summary = "Заказ пользователя ",
            description = "Заказ пользователя "
    )
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.findAllByUserId(userIDLong).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Просмотр фильма  ",
            description = "Просмотр фильма   "
    )
    @GetMapping("/playFilm")
    public String findByFilmId(@RequestHeader String userId, @RequestParam Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        Optional<Order> order1 = orderService.findFilmByUserIdAndFilmId(userIDLong, filmId);
        if (order1.isEmpty()) {
            return " Нельзя смотреть, фильм не оплачен " + filmId;
        }
        return "Приятного просмотра ";

    }

    @Operation(
            summary = "удаление заказа  ",
            description = "удаление заказа из бд   "
    )
    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader String userId, @RequestParam Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        String result = orderService.delete(userIDLong, filmId);
        if (result.equals("")) {
            return ResponseEntity.ok(new StringResponse(" фильм успешно удален из заказов пользователя "));

        } else {
            return new ResponseEntity<>(new AppError("FILM_NOT_FOUND", "Фильм не найден в заказах пользователя"), HttpStatus.NOT_FOUND);
        }

    }
    @Operation(
            summary = "Фильмы в арнде  ",
            description = "удавытаскиваем из бд заказов фильмы пользователя которые в аренде  "
    )
    @GetMapping("/rent")
    public List<Order> filmIsRent(String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.filmIsRent(userIDLong);
    }
    @Operation(
            summary = "Фильмы купленные",
            description = "вытаскиваем из бд заказов фильмы пользователя которые куплены "
    )
    @GetMapping("/sale")
    public List<Order> filmIsSale(String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.filmIsSale(userIDLong);
    }
}