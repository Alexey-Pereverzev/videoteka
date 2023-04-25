package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.CountryDto;
import ru.gb.catalogservice.converters.CountryConverter;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.services.CountryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
@Tag(name = "Страны", description = "Методы для работы со списком стран")
public class CountryController {
    private final CountryService countryService;
    private final CountryConverter countryConverter;
    @GetMapping("list_all")
    public List<CountryDto> listAll(){
        return countryService.findAll().stream().map(countryConverter::entityToDto).toList();
    }
}
