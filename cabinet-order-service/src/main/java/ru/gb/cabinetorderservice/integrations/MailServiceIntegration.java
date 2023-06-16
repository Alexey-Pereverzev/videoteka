package ru.gb.cabinetorderservice.integrations;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.dto.EmailDto;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MailServiceIntegration {
    private final WebClient mailServiceWebClient;

    public void sendMessage(EmailDto emailDto) {
        mailServiceWebClient.post()
                .uri("/api/v1/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(emailDto), EmailDto.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}