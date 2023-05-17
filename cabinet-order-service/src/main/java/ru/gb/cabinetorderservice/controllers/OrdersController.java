package ru.gb.cabinetorderservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.exceptions.ResourceNotFoundException;
import ru.gb.api.dtos.order.OrderDto;
import ru.gb.cabinetorderservice.converters.OrderConverter;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.services.OrderService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public String findByFilmId(@RequestHeader String userId, @RequestParam  Long filmId) {
        String message = "";
        Long userIDLong = Long.valueOf(userId);
        Order order = orderService.findFilmByUserIdAndFilmId(userIDLong, filmId).orElseThrow(() -> new ResourceNotFoundException(" Нельзя смотреть," +filmId));
        if (order.getType().equals("RENT")){
            if  (orderService.orderIsDelete(order)){
                // если фильм в аренде и время проката истекло
                message = "Нельзя смотреть";

            }
        }
        else message = "Приятного просмотра ";

        return  message;

    }
    @Operation(
            summary = "удаление заказа  ",
            description = "удаление заказа из бд   "
    )
    @GetMapping("/delete")
    public void delete(@RequestHeader String userId, @RequestParam  Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        orderService.delete(userIDLong, filmId);


    }
}