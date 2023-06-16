package ru.gb.cartservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.OrderDto;


@Component
@RequiredArgsConstructor
public class OrderServiceIntegration {
    private final WebClient orderServiceWebClient;


    public OrderDto findByFilmIdAndUserId(String userId, Long filmId) {
        OrderDto orderDto = orderServiceWebClient.get()
                .uri("/api/v1/orders/user_film/?filmId="+filmId)
                .header( "userId", userId)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
        return orderDto;

    }
}
