package ru.gb.catalogservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.PriceDto;
import ru.gb.catalogservice.converters.PriceConverter;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.services.PriceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    private final PriceConverter priceConverter;
    @GetMapping("list_all")
    public PriceDto getAllPrices(){
        List<Price> priceList=priceService.findAllByIsDeletedIsFalse();
        PriceDto priceDto = PriceDto.builder()
                .minPriceSale(priceList.stream().min(Integer::compare))
                .build();
        return null;
    }
}
