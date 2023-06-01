package ru.gb.notificationservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.UserDto;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
    private final WebClient authServiceWebClient;


    public UserDto findById(Long id) {
        UserDto userDto = authServiceWebClient.get()
                .uri("/api/v1/users/find_by_id?id="+id)
                //.header( "find_by_id", String.valueOf(id))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        return userDto;
    }
}
