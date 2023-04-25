package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.GenreDto;
import ru.gb.api.dtos.PriceDto;
import ru.gb.catalogservice.converters.PriceConverter;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.services.PriceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
@Tag(name = "Цены", description = "Методы для работы со списком цен")
public class PriceController {
    private final PriceService priceService;
    private final PriceConverter priceConverter;
    @GetMapping("prices_filter")
    public PriceDto getMinMaxPrices(){
        return priceConverter.entityToDto(priceService.findAllByIsDeletedIsFalse());
    }

}
