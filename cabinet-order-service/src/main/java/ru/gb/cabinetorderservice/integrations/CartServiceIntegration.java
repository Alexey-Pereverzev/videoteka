package ru.gb.cabinetorderservice.integrations;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
                .uri("/api/v1/cart/?uuid="+userId)
                .header( "userId",userId)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}