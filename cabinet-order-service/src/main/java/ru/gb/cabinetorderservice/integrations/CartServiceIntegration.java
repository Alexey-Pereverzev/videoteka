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
                .uri("/api/v1/cart/0/clear")
                .header("user_id", userId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String userId) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("user_id", userId)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}
