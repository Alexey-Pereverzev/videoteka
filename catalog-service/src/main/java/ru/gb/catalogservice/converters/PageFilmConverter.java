package ru.gb.catalogservice.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.gb.api.dtos.PageFilmDto;
import ru.gb.catalogservice.entities.Film;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PageFilmConverter {

    private final FilmConverter filmConverter;

    public PageFilmDto entityToDto(Page<Film> films) {
        PageFilmDto pageFilmDto = PageFilmDto.builder()
                .number(films.getNumber())
                .size(films.getSize())
                .contents(films.getContent().stream().map(filmConverter::entityToDto).collect(Collectors.toList()))
                .totalPages(films.getTotalPages())
                .totalElements((int) films.getTotalElements())
                .build();
        return pageFilmDto;
    }
}
