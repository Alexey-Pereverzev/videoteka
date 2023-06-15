package ru.gb.authorizationservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.exceptions.IntegrationException;

@Component
@RequiredArgsConstructor
public class MailServiceIntegration {
    private final WebClient mailServiceWebClient;

    public void sendEmailMessage(EmailDto emailDto) {
        mailServiceWebClient.post()
                .uri("/api/v1/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(emailDto), EmailDto.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public String composeVerificationLetter(String firstName, String email) {
        StringResponse response = mailServiceWebClient.get()
                .uri("/api/v1/mail/verification_code/?firstName=" + firstName
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

}
