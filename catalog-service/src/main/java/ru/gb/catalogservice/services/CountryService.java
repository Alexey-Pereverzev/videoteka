package ru.gb.catalogservice.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.repositories.CountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final Sort SORT_COUNTRY = Sort.by("title").ascending();

    public List<Country> findAll(){
        return countryRepository.findAll(SORT_COUNTRY);
    }
}
