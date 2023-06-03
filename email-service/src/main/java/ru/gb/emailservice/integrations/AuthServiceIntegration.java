package ru.gb.emailservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.dto.UserNameMailDto;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
    private final WebClient authServiceWebClient;


    public UserNameMailDto findById(Long id) {
        UserNameMailDto userNameMailDto = authServiceWebClient.get()
                .uri("/api/v1/users/get_name_and_email_by_id/?id="+id)
                //.header( "find_by_id", String.valueOf(id))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(UserNameMailDto.class)
                .block();
        return userNameMailDto;
    }
}
