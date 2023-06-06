package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.api.dtos.dto.DirectorDto;
import ru.gb.catalogservice.converters.DirectorConverter;
import ru.gb.catalogservice.exceptions.IllegalInputDataException;
import ru.gb.catalogservice.services.DirectorService;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/director")
@RequiredArgsConstructor
@Tag(name = "Режиссер", description = "Методы для работы со списком режиссеров")
public class DirectorController {
    private final DirectorService directorService;
    private final DirectorConverter directorConverter;
    @Operation(
            summary = "Вывод имени режиссера по id",
            description = "Позволяет вывести имя режиссера по заданному id"
    )
    @GetMapping("find_by_id")
    public DirectorDto findById(@RequestParam Long id){
        return directorConverter.entityToDto(directorService.findById(id));
    }
    @Operation(
            summary = "Вывод списка режиссеров",
            description = "Позволяет вывести полный список режиссеров имеющихся в БД"
    )
    @GetMapping("list_all")
    public List<DirectorDto> listAll(){
        return directorService.findAll().stream().map(directorConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Добавление нового режиссера",
            description = "Позволяет добавить в БД нового режиссера"
    )
    @PostMapping("/add_new")
    public ResponseEntity<?> addNewFilm(@RequestBody DirectorDto directorDto) {
        ResultOperation resultOperation=directorService.directorAddInVideoteka(directorDto);
        if (resultOperation.isResult()){
            return ResponseEntity.ok().body(HttpStatus.OK+" "+resultOperation.getResultDescription());
        }else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }
}
