package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.repositories.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;

    public Page<Price> findAll(){
        return priceRepository.findAll(PageRequest.of(0,3));
    }

    public List<Price> findAllByIsDeletedIsFalse(){
        return priceRepository.findAllByIsDeletedIsFalse();
    }
}
