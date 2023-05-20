package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.dto.RaitingDto;
import ru.gb.catalogservice.converters.RaitingConverter;
import ru.gb.catalogservice.entities.Raiting;
import ru.gb.catalogservice.services.RaitingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/raiting")
@RequiredArgsConstructor
@Tag(name = "Рейтинги", description = "Методы для работы с рейтингом фильмов")
public class RaitingController {
    private final RaitingService raitingService;
    private final RaitingConverter raitingConverter;
    @GetMapping("list_all")
    public List<RaitingDto> listAll(){
        return raitingService.findAll().stream().map(raitingConverter::entityToDto).toList();
    }
}
