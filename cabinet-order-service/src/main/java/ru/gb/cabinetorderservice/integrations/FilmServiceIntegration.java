package ru.gb.cabinetorderservice.integrations;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.FilmDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmServiceIntegration {
    private final WebClient filmServiceWebClient;


    public FilmDto findById(Long id) {
        FilmDto filmDto = filmServiceWebClient.get()
                .uri("/api/v1/film/find_by_id?id="+id)
                //.header( "find_by_id", String.valueOf(id))
                .retrieve()
                .bodyToMono(FilmDto.class)
                .block();
        return filmDto;
    }
}