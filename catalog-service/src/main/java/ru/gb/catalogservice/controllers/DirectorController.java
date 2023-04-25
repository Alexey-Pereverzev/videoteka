package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.DirectorDto;
import ru.gb.catalogservice.converters.DirectorConverter;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.services.CountryService;
import ru.gb.catalogservice.services.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/director")
@RequiredArgsConstructor
@Tag(name = "Режиссер", description = "Методы для работы со списком режиссеров")
public class DirectorController {
    private final DirectorService directorService;
    private final DirectorConverter directorConverter;
    @GetMapping("list_all")
    public List<DirectorDto> listAll(){
        return directorService.findAll().stream().map(directorConverter::entityToDto).toList();
    }
}
