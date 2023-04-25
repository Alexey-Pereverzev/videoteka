package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.GenreDto;
import ru.gb.catalogservice.converters.GenreConverter;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.services.FilmService;
import ru.gb.catalogservice.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
@Tag(name = "Жанры", description = "Методы для работы со списком жанров")
public class GenreController {
    private final GenreService genreService;
    private final GenreConverter genreConverter;
    @GetMapping("list_all")
    public List<GenreDto> listAll(){
        return genreService.findAll().stream().map(genreConverter::entityToDto).toList();
    }
}
