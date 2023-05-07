package ru.gb.cabinetorderservice.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.order.OrderDto;
import ru.gb.cabinetorderservice.converters.OrderConverter;
import ru.gb.cabinetorderservice.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы" , description = "Методы работы с заказами")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        orderService.createOrder(userIDLong);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.findOrdersByUserId(userIDLong).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
