package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.catalogservice.converters.CountryConverter;
import ru.gb.catalogservice.services.CountryService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
@Tag(name = "Страны", description = "Методы для работы со списком стран")
public class CountryController {
    private final CountryService countryService;
    private final CountryConverter countryConverter;

    @GetMapping("find_by_id")
    public CountryDto findById(@RequestParam Long id){
        return countryConverter.entityToDto(countryService.findById(id));
    }

    @GetMapping("list_all")
    public List<CountryDto> listAll(){
        return countryService.findAll().stream().map(countryConverter::entityToDto).toList();
    }
}
