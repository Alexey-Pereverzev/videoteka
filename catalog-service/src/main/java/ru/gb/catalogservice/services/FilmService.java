package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.repositories.DirectorRepository;
import ru.gb.catalogservice.repositories.FilmRepository;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final int FILM_PAGE_SIZE=10;
    private final FilmRepository filmRepository;

    public Page<Film> findAll(int currentPage){
        return filmRepository.findAll(PageRequest.of(currentPage,FILM_PAGE_SIZE));
    }
}
