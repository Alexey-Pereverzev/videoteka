package ru.gb.cartservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.api.dtos.FilmDto;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class FilmServiceIntegrationForCart {
    private final RestTemplate restTemplate;

    @Value("${integrations.catalog-service.url}")
    private String filmServiceUrl;

    public Optional<FilmDto> findById(Long id) {
        FilmDto filmDto = restTemplate.getForObject(filmServiceUrl + "/api/v1/film/" + id, FilmDto.class);
        return Optional.ofNullable(filmDto);
    }
}
