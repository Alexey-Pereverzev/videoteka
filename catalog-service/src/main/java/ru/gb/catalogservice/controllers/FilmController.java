package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.MinMaxYearDto;
import ru.gb.api.dtos.PageFilmDto;
import ru.gb.catalogservice.converters.FilmConverter;
import ru.gb.catalogservice.converters.PageFilmConverter;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.AppError;
import ru.gb.catalogservice.exceptions.IncorrectFilterParametrException;
import ru.gb.catalogservice.services.*;
import ru.gb.catalogservice.utils.ResultOperation;

import java.time.LocalDate;
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
    private final CountryService countryService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final PriceService priceService;
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
        List<Country> countries;
        if (filterCountryList==null || filterCountryList.length==0){
            countries=countryService.findAll();
        }else {
            countries = countryService.findByFilter(filterCountryList);
        }
        List<Director> directors;
        if (filterDirectorList==null || filterDirectorList.length==0){
            directors=directorService.findAll();
        }else{
            String[] filterDirectorFirstName=new String[filterDirectorList.length];
            String[] filterDirectorLastName=new String[filterDirectorList.length];
            for (int i=0;i< filterDirectorList.length;i++){
                String[] s=filterDirectorList[i].split(" ");
                if (s.length==2){
                    filterDirectorFirstName[i]=s[0];
                    filterDirectorLastName[i]=s[1];
                }else if(s.length>2){
                    filterDirectorFirstName[i]=s[0];
                    filterDirectorLastName[i]="";
                    for (int ii=1;ii<s.length;ii++){
                        if (ii!=1){
                            filterDirectorLastName[i]=filterDirectorLastName[i]+" ";
                        }
                        filterDirectorLastName[i]=filterDirectorLastName[i]+s[ii];
                    }
                }else{
                    throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
                }
            }
//            System.out.println(Arrays.toString(filterDirectorFirstName)+Arrays.toString(filterDirectorLastName));
            directors=directorService.findByFilter(filterDirectorFirstName,filterDirectorLastName);
        }
        List<Genre> genres;
        if (filterGenreList==null || filterGenreList.length==0){
            genres=genreService.findAll();
        }else {
            genres = genreService.findByFilter(filterGenreList);
        }
        System.out.println(startPremierYear);
        System.out.println(endPremierYear);
        if (startPremierYear==null||startPremierYear<1900){
            startPremierYear=1900;
        }
        if (endPremierYear==null||endPremierYear>LocalDate.now().getYear()){
            endPremierYear=LocalDate.now().getYear();
        }
        List<Price> prices;
        if (isSale!=null && minPrice!=null && maxPrice!=null){
            if (isSale){
                prices=priceService.findByFilterSalePrice(minPrice,maxPrice);
            }else{
                prices=priceService.findByFilterRentPrice(minPrice,maxPrice);
            }
            return filmService.findByFilter(currentPage,countries,directors,genres,
                    startPremierYear,endPremierYear,prices).map(filmConverter::entityToDto);
        }else{
            throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
        }
    }

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
        List<Country> countries;
        if (filterCountryList==null || filterCountryList.length==0){
            countries=countryService.findAll();
        }else {
            countries = countryService.findByFilter(filterCountryList);
        }
        List<Director> directors;
        if (filterDirectorList==null || filterDirectorList.length==0){
            directors=directorService.findAll();
        }else{
            String[] filterDirectorFirstName=new String[filterDirectorList.length];
            String[] filterDirectorLastName=new String[filterDirectorList.length];
            for (int i=0;i< filterDirectorList.length;i++){
                String[] s=filterDirectorList[i].split(" ");
                if (s.length==2){
                    filterDirectorFirstName[i]=s[0];
                    filterDirectorLastName[i]=s[1];
                }else if(s.length>2){
                    filterDirectorFirstName[i]=s[0];
                    filterDirectorLastName[i]="";
                    for (int ii=1;ii<s.length;ii++){
                        if (ii!=1){
                            filterDirectorLastName[i]=filterDirectorLastName[i]+" ";
                        }
                        filterDirectorLastName[i]=filterDirectorLastName[i]+s[ii];
                    }
                }else{
                    throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
                }
            }
            directors=directorService.findByFilter(filterDirectorFirstName,filterDirectorLastName);
        }
        List<Genre> genres;
        if (filterGenreList==null || filterGenreList.length==0){
            genres=genreService.findAll();
        }else {
            genres = genreService.findByFilter(filterGenreList);
        }
        System.out.println(startPremierYear);
        System.out.println(endPremierYear);
        if (startPremierYear==null||startPremierYear<1900){
            startPremierYear=1900;
        }
        if (endPremierYear==null||endPremierYear>LocalDate.now().getYear()){
            endPremierYear=LocalDate.now().getYear();
        }
        List<Price> prices;
        if (isSale!=null && minPrice!=null && maxPrice!=null){
            if (isSale){
                prices=priceService.findByFilterSalePrice(minPrice,maxPrice);
            }else{
                prices=priceService.findByFilterRentPrice(minPrice,maxPrice);
            }
            return pageFilmConverter.entityToDto(filmService.findByFilter(currentPage,countries,directors,genres,
                    startPremierYear,endPremierYear,prices));
        }else{
            throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
        }
    }



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
