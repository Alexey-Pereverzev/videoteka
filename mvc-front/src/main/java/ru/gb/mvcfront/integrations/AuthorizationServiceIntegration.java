package ru.gb.mvcfront.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.JwtRequest;
import ru.gb.api.dtos.JwtResponse;

@Component
@RequiredArgsConstructor
public class AuthorizationServiceIntegration {
    private final WebClient authorizationServiceWebClient;

    public JwtResponse getToken(String username, String password) {
        JwtRequest jwtRequest=new JwtRequest(username,password);
        return authorizationServiceWebClient.post()
                .uri("/authenticate")
                .syncBody(jwtRequest)
                .retrieve()
                .bodyToMono(JwtResponse.class)
                .block();
    }

}
