package ru.gb.cabinetorderservice.integrations;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.cart.CartDto;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MailServiceIntegration {
    private final WebClient mailServiceWebClient;

    public void createMessage(String userId) {
        mailServiceWebClient.get()
                .uri("/api/v1/mail/send")
                .header("userId", userId)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(100))
                .block();
    }

}