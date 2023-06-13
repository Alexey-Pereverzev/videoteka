package ru.gb.authorizationservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.exceptions.IntegrationException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MailServiceIntegration {
    private final WebClient mailServiceWebClient;

    public String composeVerificationLetter(String firstName, String email) {
        ResponseEntity<?> response = mailServiceWebClient.get()
                .uri("/api/v1/mail/composeVerificationLetter/?firstName=" + firstName + "&email=" + email)
                .retrieve()
                .toBodilessEntity()
                .block();

        if (!(response==null) && response.getStatusCode() == HttpStatus.OK) {
            return ((StringResponse) Objects.requireNonNull(response.getBody())).getValue();
        } else {
            throw new IntegrationException("Ошибка сервиса уведомлений");
        }
    }
}
