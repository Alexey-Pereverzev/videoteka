package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.repositories.DirectorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final Sort SORT_DIRECTOR = Sort.by("lastName").ascending();
    public List<Director> findAll(){
        return directorRepository.findAll(SORT_DIRECTOR);
    }
}
