package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.repositories.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;
    public Price findById(Long id){
        return priceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Цена с id="+id+" не найдена"));
    }

    public Page<Price> findAll(){
        return priceRepository.findAll(PageRequest.of(0,3));
    }

    public List<Price> findAllByIsDeletedIsFalseForFilter(int minSalePrice, int maxSalePrice,
                                                 int minRentPrice, int maxRentPrice){
        return priceRepository.findAllByIsDeletedIsFalseAndPriceSaleBetweenAndPriceRentBetween(minSalePrice,
                maxSalePrice,minRentPrice,maxRentPrice);
    }

    public List<Price> findAllByIsDeletedIsFalse(){
        return priceRepository.findAllByIsDeletedIsFalse();
    }
}
