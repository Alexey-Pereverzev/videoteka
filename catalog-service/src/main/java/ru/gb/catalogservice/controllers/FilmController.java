package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.MinMaxYearDto;
import ru.gb.api.dtos.dto.PageFilmDto;
import ru.gb.catalogservice.converters.FilmConverter;
import ru.gb.catalogservice.converters.PageFilmConverter;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.services.*;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "Методы для работы со списком фильмов")
public class FilmController {
    private final FilmService filmService;
    private final FilmConverter filmConverter;
    private final PageFilmConverter pageFilmConverter;

    @Operation(
            summary = "Вывод данных фильма по id",
            description = "Позволяет вывести данные фильма по заданному id"
    )
    @GetMapping("find_by_id")
    public FilmDto findById(@RequestParam Long id){
        return filmConverter.entityToDto(filmService.findById(id));
    }

    @GetMapping("find_by_title_part")
    public Page<FilmDto> findByTitlePart(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage,
                                         @RequestParam (name="titlePart",required = false) String titlePart){
        if (titlePart==null)
        {
            titlePart="";
        }
        return filmService.findByTitlePart(currentPage, titlePart).map(filmConverter::entityToDto);
    }
    @Operation(
            summary = "Вывод списка фильмов для главной страницы",
            description = "Позволяет вывести полный список стран, имеющихся в БД с применением условий фильтров. Используется для подготовки главной страницы"
    )
    @GetMapping("list_all")
    public Page<FilmDto> listAll(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage,
                                 @RequestParam (name="filterCountryList",required = false) String[] filterCountryList,
                                 @RequestParam (name="filterDirectorList",required = false) String[] filterDirectorList,
                                 @RequestParam (name="filterGenreList",required = false) String[] filterGenreList,
                                 @RequestParam (name="startPremierYear",required = false)Integer startPremierYear,
                                 @RequestParam (name="endPremierYear",required = false)Integer endPremierYear,
                                 @RequestParam (name="isSale",required = false)Boolean isSale,
                                 @RequestParam (name="minPrice",required = false)Integer minPrice,
                                 @RequestParam (name="maxPrice",required = false)Integer maxPrice){

        return filmService.listAll(currentPage,filterCountryList,filterDirectorList,filterGenreList,
                startPremierYear,endPremierYear,isSale,minPrice,maxPrice).map(filmConverter::entityToDto);
    }
    @Operation(
            summary = "Вывод списка фильмов для главной страницы",
            description = "Позволяет вывести полный список стран, имеющихся в БД с применением условий фильтров. Используется для подготовки главной страницы"
    )
    @GetMapping("list_all_dto")
    public PageFilmDto listAllDto(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage,
                               @RequestParam (name="filterCountryList",required = false) String[] filterCountryList,
                               @RequestParam (name="filterDirectorList",required = false) String[] filterDirectorList,
                               @RequestParam (name="filterGenreList",required = false) String[] filterGenreList,
                               @RequestParam (name="startPremierYear",required = false)Integer startPremierYear,
                               @RequestParam (name="endPremierYear",required = false)Integer endPremierYear,
                               @RequestParam (name="isSale",required = false)Boolean isSale,
                               @RequestParam (name="minPrice",required = false)Integer minPrice,
                               @RequestParam (name="maxPrice",required = false)Integer maxPrice){
        return pageFilmConverter.entityToDto(filmService.listAll(currentPage,filterCountryList,filterDirectorList,filterGenreList,
                startPremierYear,endPremierYear,isSale,minPrice,maxPrice));
    }
    @Operation(
            summary = "Добавление фильма в БД",
            description = "Позволяет добавлять фильмы в БД"
    )
    @PostMapping("/add_new")
    public ResponseEntity<?> addProduct(@RequestBody FilmDto filmDto) {
        ResultOperation resultOperation=filmService.filmAddInVideoteka(filmDto);
        if (resultOperation.isResult()){
            return ResponseEntity.ok().body(HttpStatus.OK+" "+resultOperation.getResultDescription());
        }else {
            return new ResponseEntity<>(new AppError("ILLEGAL INPUT DATA", resultOperation.getResultDescription()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("min_max_year")
    public MinMaxYearDto findMinAndMaxYear() {
        List<Integer> allYears = filmService.findAll().stream().map(Film::getPremierYear).toList();
        int minYear = Collections.min(allYears);
        int maxYear = Collections.max(allYears);
        return new MinMaxYearDto(minYear, maxYear);
    }

}
