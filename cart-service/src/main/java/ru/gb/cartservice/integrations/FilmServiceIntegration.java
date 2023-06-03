package ru.gb.cartservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.FilmDto;

@Component
@RequiredArgsConstructor
public class FilmServiceIntegration {
    private final WebClient filmServiceWebClient;


    public FilmDto findById(Long id) {
        FilmDto filmDto = filmServiceWebClient.get()
                .uri("/api/v1/film/find_by_id/?id="+id)
                //.header( "find_by_id", String.valueOf(id))
                .retrieve()
                .bodyToMono(FilmDto.class)
                .block();
        return filmDto;
    }
}
