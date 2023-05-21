package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.RaitingDto;
import ru.gb.catalogservice.converters.RaitingConverter;
import ru.gb.catalogservice.exceptions.AppError;
import ru.gb.catalogservice.services.RaitingService;
import ru.gb.catalogservice.utils.ResultOperation;

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

    @PostMapping("/add_new")
    public ResponseEntity<?> addFilmRating(@RequestBody RaitingDto raitingDto) {
        ResultOperation resultOperation=raitingService.addFilmRating(raitingDto);
        if (resultOperation.isResult()){
            return ResponseEntity.ok().body(HttpStatus.OK+" "+resultOperation.getResultDescription());
        }else {
            return new ResponseEntity<>(new AppError("ILLEGAL INPUT DATA", resultOperation.getResultDescription()), HttpStatus.BAD_REQUEST);
        }
    }
}
