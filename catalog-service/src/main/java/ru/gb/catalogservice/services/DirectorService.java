package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.repositories.DirectorRepository;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    public Page<Director> findAll(){
        return directorRepository.findAll(PageRequest.of(0,3));
    }
}
