package ru.gb.cabinetorderservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.api.dtos.exceptions.ResourceNotFoundException;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.AuthServiceIntegration;
import ru.gb.cabinetorderservice.integrations.CartServiceIntegration;
import ru.gb.cabinetorderservice.integrations.MailServiceIntegration;
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
    private final MailServiceIntegration mailServiceIntegration;
    private final AuthServiceIntegration authServiceIntegration;
    private static final String SERVER_TIME_ZONE = "Europe/Moscow";
    private final Integer RENT_HOURS = 24;


    @Transactional
    public String createOrder(String userId) {
        EmailDto emailDto = new EmailDto();

        try {
            CartDto currentCart = cartServiceIntegration.getCart(userId);
            Long userIDLong = Long.valueOf(userId);
            UserNameMailDto userNameMailDto = authServiceIntegration.findById(userIDLong);
            emailDto.setEmail(userNameMailDto.getEmail());
            emailDto.setFirstName(userNameMailDto.getFirstName());
            String message = "";
            emailDto.setSubject("Оформление заказа");
            if (currentCart.getItems().size() <= 0) {
                return "Заказ не сохранен - корзина пуста";
            }

            for (CartItemDto cartItemDto : currentCart.getItems()) {
                Order order = new Order();
                order.setUserId(userIDLong);
                order.setPrice(cartItemDto.getPrice());
                order.setFilmId(cartItemDto.getFilmId());
                if (!cartItemDto.isSale()) {
                    order.setType("RENT");
                    // текущее время
                    LocalDateTime dateStart = Instant.ofEpochMilli(System.currentTimeMillis())
                            .atZone(ZoneId.of(SERVER_TIME_ZONE)).toLocalDateTime();
                    order.setRentStart(dateStart);
                    // к текущей дате прибавили 24 часа
                    order.setRentEnd(dateStart.plusHours(RENT_HOURS));// завести константу,
                    message = String.valueOf(dateStart.plusHours(RENT_HOURS));
                    emailDto.setMessage( "Ваш заказ успешно оформлен. Вы оплатили прокат фильма \n -" + cartItemDto.getTitle()
                            + " Срок аренды составляет 24 часа и закончится " + message + "\n \n Приятного просмотра ");

                } else {
                    order.setType("SALE");
                    emailDto.setMessage( "Ваш заказ успешно оформлен. Вы купили фильм \n - " + cartItemDto.getTitle() + "\n \n Приятного просмотра ");

                }
                ordersRepository.save(order);
                mailServiceIntegration.sendMessage(emailDto);

            }

            cartServiceIntegration.clearUserCart(userId);


            return "Заказ успено сохранен в БД";
        }
        catch (Exception e){
            return "Ошибка интеграции";
        }
    }

    @Transactional
    // возвращаем заказы пользователя проверяем если вермя проката изтекло то не выводим
    public List<Order> findAllByUserId(Long userId) {
        List<Order> orders = ordersRepository.findAllByUserId(userId);// возвращает список заказов с полем isDelete- false
        Iterator<Order> orderIterator = orders.iterator();
        while (orderIterator.hasNext()){
            Order orderNext= orderIterator.next();
            if (orderNext.getType().equals("RENT")) {
                // если время проката истекло то ставим статус в поле isDelete - false
                // пересохраняем фильм
                softDeleteOfOrderInRent(orderNext);
            }
        }

        return ordersRepository.findAllByUserId(userId);
    }
    @Transactional
    public boolean softDeleteOfOrderInRent(Order order){
        LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(SERVER_TIME_ZONE)).toLocalDateTime();
        if (order.getRentEnd().isBefore(dateNow)) {
            order.setDeleted(true);
            order.setDeletedWhen(dateNow);
            // пересохраняем заказ пользователя
            ordersRepository.save(order);
            return true;
        }
        return false;
    }
    @Transactional
    public void softDeleteOfOrder(Order order){
        LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(SERVER_TIME_ZONE)).toLocalDateTime();
            order.setDeleted(true);
            order.setDeletedWhen(dateNow);
            // пересохраняем заказ пользователя
            ordersRepository.save(order);

    }

    public String delete(Long userId, Long filmId) {
        List<Order> orders = ordersRepository.findByUserIdAndFilmId(userId, filmId);
        if (orders.size()>0) {
            Iterator<Order> orderIterator = orders.iterator();
            while (orderIterator.hasNext()){
                Order orderNext= orderIterator.next();
                softDeleteOfOrder(orderNext);
                    // если время проката истекло то ставим статус в поле isDelete - false
                    // пересохраняем фильм

                }
            return "Фильм перезаписан в статусе удален";
        }
        else {
            return " Этого фильма нет в бд," + filmId;
        }
    }


    public Optional<Order> findFilmByUserIdAndFilmId(Long userId, Long filmId) {
        List<Order> orders = ordersRepository.findByUserIdAndFilmId(userId, filmId);
        if (orders.size()>0){
            return Optional.ofNullable(orders.get(0));
        }
        else return Optional.empty();
    }
    public List<Order> filmIsRent (Long userId){
        return ordersRepository.findAllByUserIfFilmIsRent(userId);
    }
    public List<Order> filmIsSale (Long userId){
        return ordersRepository.findAllByUserIfFilmIsSale(userId);
    }
}