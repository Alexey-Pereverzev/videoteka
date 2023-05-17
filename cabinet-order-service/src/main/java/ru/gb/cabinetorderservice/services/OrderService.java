package ru.gb.cabinetorderservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.exceptions.ResourceNotFoundException;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.CartServiceIntegration;
import ru.gb.cabinetorderservice.repositories.OrdersRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

        CartDto currentCart = cartServiceIntegration.getCart(userIdString);
        for (CartItemDto cartItemDto : currentCart.getItems()) {
            Order order = new Order();
            order.setUserId(userId);
            order.setPrice(cartItemDto.getPrice());
            order.setFilmId(cartItemDto.getFilmId());
            if (!cartItemDto.isSale()) {
                order.setType("RENT");
                // текущее время
                LocalDateTime dateStart = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
                order.setRentStart(dateStart);
                // к текущей дате прибавили 24 часа
                order.setRentEnd(dateStart.plusHours(RENT_HOURS));// завести константу,

            } else
                order.setType("SALE");
            ordersRepository.save(order);
        }


        cartServiceIntegration.clearUserCart(userIdString);
    }

    @Transactional
    // возвращаем заказы пользователя проверяем если вермя проката изтекло то не выводим
    public List<Order> findAllByUserId(Long userId) {
        List<Order> orders = ordersRepository.findAllByUserId(userId);// возвращает список заказов с полем isDelete- false
        Iterator<Order> orderIterator = orders.iterator();
        while (orderIterator.hasNext()){
            LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
            if (orderIterator.next().getType().equals("RENT")) {
                // если время проката истекло то ставим статус в поле isDelete - false
                // пересохраняем фильм
                orderIsDelete(orderIterator.next());
            }
        }

//        for (Order order : orders) {
//            LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
//            if (order.getType().equals("RENT")) {
//                if (order.getRentEnd().isBefore(dateNow)) {
//                    order.setDeleted(true);
//                    order.setCreatedWhen(LocalDateTime.now());
//                    // пересохраняем заказ пользователя
//                    ordersRepository.save(order);
//                }
//            }

        return ordersRepository.findAllByUserId(userId);
    }
    public boolean orderIsDelete(Order order){
        LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(SERVER_TIME_ZONE).systemDefault()).toLocalDateTime();
        if (order.getRentEnd().isBefore(dateNow)) {
            order.setDeleted(true);
            order.setCreatedWhen(LocalDateTime.now());
            // пересохраняем заказ пользователя
            ordersRepository.save(order);
            return true;
        }
        return false;
    }

    public void delete(Long userId, Long filmId) {
        Order order = ordersRepository.findByUserIdAndFilmId(userId, filmId).orElseThrow(() -> new ResourceNotFoundException(" Этого фильма нет в бд," + filmId));;
        ordersRepository.deleteById(order.getId());
    }

    public Optional<Order> findFilmByUserIdAndFilmId(Long userId, Long filmId) {
        return ordersRepository.findByUserIdAndFilmId(userId, filmId);
    }
}