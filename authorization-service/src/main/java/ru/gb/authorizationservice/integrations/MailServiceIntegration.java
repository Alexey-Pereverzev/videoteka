package ru.gb.authorizationservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.exceptions.IntegrationException;

import java.time.Duration;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MailServiceIntegration {
    private final WebClient mailServiceWebClient;

    public String composeVerificationLetter(String firstName, String email) {
        StringResponse response = mailServiceWebClient.get()
                .uri("/api/v1/mail/composeVerificationLetter/?firstName=" + firstName
                        + "&email=" + email)
                .retrieve()
                .bodyToMono(StringResponse.class)
                .block();

        if (!(response == null)) {
            return response.getValue();
        } else {
            throw new IntegrationException("Ошибка сервиса уведомлений");
        }
    }

    public String composePasswordLetter(String email, String firstName) {
        StringResponse response = mailServiceWebClient.get()
                .uri("/api/v1/mail/composePasswordLetter/?firstName=" + firstName + "&email=" + email)
                .retrieve()
                .bodyToMono(StringResponse.class)
                .block();

        if (!(response == null)) {
            return response.getValue();
        } else {
            throw new IntegrationException("Ошибка сервиса уведомлений");
        }
    }

    public String composeRegistrationLetter(String firstName, String userName, String email) {
        StringResponse response = mailServiceWebClient.get()
                .uri("/api/v1/mail/composeRegistrationLetter/?firstName=" + firstName
                        + "&userName=" + userName + "&email=" + email)
                .retrieve()
                .bodyToMono(StringResponse.class)
                .block();

        if (!(response == null)) {
            return response.getValue();
        } else {
            throw new IntegrationException("Ошибка сервиса уведомлений");
        }
    }
}
