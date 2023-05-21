package ru.gb.catalogservice.services;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.PageFilmDto;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.IncorrectFilterParametrException;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.FilmRepository;
import ru.gb.catalogservice.utils.ResultOperation;

import java.time.LocalDate;
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

    public Page<Film> findByTitlePart(int currentPage, String titlepart) {
        return filmRepository.findByTitlePart(PageRequest.of(currentPage, FILM_PAGE_SIZE, sort), "%" + titlepart + "%");
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

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Page<Film> listAll (int currentPage, String[] filterCountryList,String[] filterDirectorList,
                                  String[] filterGenreList, Integer startPremierYear, Integer endPremierYear,
                                  Boolean isSale, Integer minPrice, Integer maxPrice){
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
        if (startPremierYear==null||startPremierYear<1900){
            startPremierYear=1900;
        }
        if (endPremierYear==null||endPremierYear> LocalDate.now().getYear()){
            endPremierYear=LocalDate.now().getYear();
        }
        List<Price> prices;
        if (isSale!=null && minPrice!=null && maxPrice!=null){
            if (isSale){
                prices=priceService.findByFilterSalePrice(minPrice,maxPrice);
            }else{
                prices=priceService.findByFilterRentPrice(minPrice,maxPrice);
            }
            return findByFilter(currentPage,countries,directors,genres,
                    startPremierYear,endPremierYear,prices);
        }else{
            throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
        }
    }

}