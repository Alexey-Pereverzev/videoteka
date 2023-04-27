package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.CountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final Sort SORT_COUNTRY = Sort.by("title").ascending();

    public Country findById(Long id){
        return countryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Страна с id="+id+" не найдена"));
    }

    public List<Country> findByTitle(String title){
        return countryRepository.findAllByTitle(title);
    }

    public List<Country> findByFilter(String[] strings){
        return countryRepository.findAllByTitleIsIn(strings);
    }
    public List<Country> findAll(){
        return countryRepository.findAll(SORT_COUNTRY);
    }
}
