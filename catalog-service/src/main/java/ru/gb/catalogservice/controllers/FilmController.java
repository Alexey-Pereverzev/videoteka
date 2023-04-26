package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.DirectorDto;
import ru.gb.api.dtos.FilmDto;
import ru.gb.catalogservice.converters.FilmConverter;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Film;

import ru.gb.catalogservice.services.CountryService;
import ru.gb.catalogservice.services.FilmService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "Методы для работы со списком фильмов")
public class FilmController {
    private final FilmService filmService;
    private final FilmConverter filmConverter;
    private final CountryService countryService;
    @GetMapping("find_by_id")
    public FilmDto findById(@RequestParam Long id){
        return filmConverter.entityToDto(filmService.findById(id));
    }
    @GetMapping("list_all")
    public Page<FilmDto> listAll(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage){
        String[] filter = {"Россия","Германия"};
        List<Country> countries = countryService.findCountryList(filter);
        return filmService.findByFilter(currentPage,countries).map(filmConverter::entityToDto);
    }

}
