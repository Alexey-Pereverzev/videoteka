package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.FilmDto;
import ru.gb.catalogservice.converters.FilmConverter;
import ru.gb.catalogservice.services.FilmService;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "Методы для работы со списком фильмов")
public class FilmController {
    private final FilmService filmService;
    private final FilmConverter filmConverter;
    @GetMapping("list_all")
    public Page<FilmDto> getUserOrders(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage){
        return filmService.findAll(currentPage).map(filmConverter::entityToDto);
    }
}
