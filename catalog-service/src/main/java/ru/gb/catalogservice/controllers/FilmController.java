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
import ru.gb.catalogservice.entities.*;

import ru.gb.catalogservice.services.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "Методы для работы со списком фильмов")
public class FilmController {
    private final FilmService filmService;
    private final FilmConverter filmConverter;
    private final CountryService countryService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final PriceService priceService;
    @GetMapping("find_by_id")
    public FilmDto findById(@RequestParam Long id){
        return filmConverter.entityToDto(filmService.findById(id));
    }
    @GetMapping("list_all")
    public Page<FilmDto> listAll(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage){
        String[] filterCountry = {"Россия","Германия","США"};
        String[] filterDirectorFirstName = {"Александр","Алексей","Антон"};
        String[] filterDirectorLastName = {"Прошкин","Герман","Васильев"};
        String[] filterGenre = {"Криминал","Комедия"};
        List<Country> countries = countryService.findCountryList(filterCountry);
        List<Director> directors = directorService.findByFilter(filterDirectorFirstName,filterDirectorLastName);
        List<Genre> genres =  genreService.findByFilter(filterGenre);
        int premierYear=2002;
        int minSalePrice=99;
        int maxSalePrice=119;
        int minRentPrice=19;
        int maxRentPrice=29;
        List<Price> prices=priceService.findAllByIsDeletedIsFalseForFilter(minSalePrice,maxSalePrice,minRentPrice,maxRentPrice);
        System.out.println(prices);
        return filmService.findByFilter(currentPage,countries,directors,genres,premierYear).map(filmConverter::entityToDto);
    }

}
