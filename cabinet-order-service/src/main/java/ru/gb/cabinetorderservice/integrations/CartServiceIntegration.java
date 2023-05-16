package ru.gb.cabinetorderservice.integrations;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.cart.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String userId) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/clear?uuid="+userId)
                .header("userId", userId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getCart(String userId) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart?uuid="+userId)
                .header( "userId",userId)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}