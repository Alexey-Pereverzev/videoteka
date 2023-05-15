package ru.gb.cabinetorderservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.CartServiceIntegration;
import ru.gb.cabinetorderservice.repositories.OrdersRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private static final String SERVER_TIME_ZONE = "Europe/Moscow";
    private final Integer RENT_HOURS = 24;


    @Transactional
    public void createOrder(Long userId) {

        String userIdString = String.valueOf(userId);

        CartDto currentCart = cartServiceIntegration.getUserCart(userIdString);
        for(CartItemDto cartItemDto: currentCart.getItems())
        {
            Order order = new Order();
            order.setUserId(userId);
            order.setPrice(cartItemDto.getPrice());
            order.setFilmId(cartItemDto.getFilmId());
            if (!cartItemDto.isSale()){
                order.setType("RENT");
                // текущее время
                LocalDateTime dateStart = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
                order.setRentStart(dateStart);
                // к текущей дате прибавили 24 часа
                order.setRentEnd(dateStart.plusHours(RENT_HOURS));// завести константу,

            }
            else
                order.setType("SALE");
            ordersRepository.save(order);
        }


        cartServiceIntegration.clearUserCart(userIdString);
    }
    // возвращаем заказы пользователя проверяем если вермя проката изтекло то не выводим
    public List<Order> findAllOrdersByUserId(Long userId) {
        List<Order>  orders = ordersRepository.findAllByUserId(userId);
        for (Order order: orders){
            LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
            if (order.getType().equals("RENT")){
                if (order.getRentEnd().isBefore(dateNow)){
                    orders.remove(order);
                }
            }
        }
        return orders;
    }
}