package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.FilmRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final int FILM_PAGE_SIZE=10;
    private final FilmRepository filmRepository;
    Sort sort = Sort.by("title").ascending();
    public Film findById(Long id){
        return filmRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Фильм с id="+id+" не найден"));
    }
    public Page<Film> findByFilter(int currentPage, List<Country> countries, List<Director> directors, List<Genre> genres,
                                   int startPremierYear, int endPremierYear,
                                   List<Price> prices){
        return filmRepository.findWithFilter(PageRequest.of(currentPage,FILM_PAGE_SIZE,sort),countries,
                directors,genres,startPremierYear,endPremierYear,prices);
    }
}
