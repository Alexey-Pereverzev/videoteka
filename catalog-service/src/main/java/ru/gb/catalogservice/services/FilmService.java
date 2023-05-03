package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.FilmDto;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.FilmRepository;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final int FILM_PAGE_SIZE = 10;
    private final FilmRepository filmRepository;
    private final CountryService countryService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final PriceService priceService;
    Sort sort = Sort.by("title").ascending();

    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Фильм с id=" + id + " не найден"));
    }

    public Page<Film> findByFilter(int currentPage, List<Country> countries, List<Director> directors, List<Genre> genres,
                                   int startPremierYear, int endPremierYear,
                                   List<Price> prices) {
        return filmRepository.findWithFilter(PageRequest.of(currentPage, FILM_PAGE_SIZE, sort), countries,
                directors, genres, startPremierYear, endPremierYear, prices);
    }

    public ResultOperation filmAddInVideoteka(FilmDto filmDto){
        ResultOperation resultOperation=new ResultOperation();
        resultOperation.setResultDescription("");
        String temp="";
        if (filmDto.getCountry()==null || filmDto.getCountry().size()==0){
            temp=resultOperation.getResultDescription()+"Не задана страна"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getDirector()==null || filmDto.getDirector().size()==0){
            temp=resultOperation.getResultDescription()+"Не задан режиссер"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getGenre()==null || filmDto.getGenre().size()==0){
            temp=resultOperation.getResultDescription()+"Не задан жанр"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getDescription()==null || filmDto.getDescription().length()==0){
            temp=resultOperation.getResultDescription()+"Не задано описание фильма"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getPremierYear()==null || filmDto.getPremierYear()<1895){
            temp=resultOperation.getResultDescription()+"Не задан год премьеры"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getTitle()==null || filmDto.getTitle().length()==0){
            temp=resultOperation.getResultDescription()+"Не задано название фильма"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getRentPrice()==null || filmDto.getRentPrice()<0){
            temp=resultOperation.getResultDescription()+"Не задана цена аренды"+"-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getSalePrice()==null || filmDto.getSalePrice()<0){
            temp=resultOperation.getResultDescription()+"Не задана цена продажи";
            resultOperation.setResultDescription(temp);
        }
        if (resultOperation.getResultDescription().length()==0){
            Film film = new Film();
            film.setTitle(filmDto.getTitle());
            film.setPremierYear(filmDto.getPremierYear());
            if (filmDto.getImageUrlLink() != null) {
                film.setImageUrlLink(filmDto.getImageUrlLink());
            }
            String[] tempArray= filmDto.getCountry().toArray(new String[0]);
            List<Country> countries=countryService.findByFilter(tempArray);
            film.setCountries(countries);
            String[] filterDirectorFirstName = new String[filmDto.getDirector().size()];
            String[] filterDirectorLastName = new String[filmDto.getDirector().size()];
            for (int i = 0; i < filmDto.getDirector().size(); i++) {
                String[] s = filmDto.getDirector().get(i).split(" ");
                if (s.length == 2) {
                    filterDirectorFirstName[i] = s[0];
                    filterDirectorLastName[i] = s[1];
                } else if (s.length > 2) {
                    filterDirectorFirstName[i] = s[0];
                    filterDirectorLastName[i] = "";
                    for (int ii = 1; ii < s.length; ii++) {
                        if (ii != 1) {
                            filterDirectorLastName[i] = filterDirectorLastName[i] + " ";
                        }
                        filterDirectorLastName[i] = filterDirectorLastName[i] + s[ii];
                    }
                }
            }
            List<Director> directors = directorService.findByFilter(filterDirectorFirstName, filterDirectorLastName);
            film.setDirectors(directors);
            tempArray= filmDto.getGenre().toArray(new String[0]);
            List<Genre> genres=genreService.findByFilter(tempArray);
            film.setGenres(genres);
            film.setDescription(filmDto.getDescription());
            film=filmRepository.saveAndFlush(film);
            Price price=new Price();
            price.setPriceSale(filmDto.getSalePrice());
            price.setPriceRent(filmDto.getRentPrice());
            price.setFilm(film);
            priceService.save(price);
            resultOperation.setResultDescription("Фильм добавлен в БД");
            resultOperation.setResult(true);
        }else{
            resultOperation.setResult(false);
        }
        return resultOperation;
    }
}