package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Page<Genre> findAll(){
        return genreRepository.findAll(PageRequest.of(0,3));
    }
}
