package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Вывод таблицы оценок",
            description = "Позволяет вывести ВСЕ записи таблицы оценок и комментариев"
    )
    @GetMapping("list_all")
    public List<RaitingDto> listAll() {
        return raitingService.findAll().stream().map(raitingConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Добавление оценки и комментария к фильму",
            description = "Позволяет добавить оценку и комментарий пользователя к фильму"
    )
    @PostMapping("/add_new")
    public ResponseEntity<?> addFilmRating(@RequestBody RaitingDto raitingDto) {
        ResultOperation resultOperation = raitingService.addFilmRating(raitingDto);
        if (resultOperation.isResult()) {
            return ResponseEntity.ok().body(HttpStatus.OK + " " + resultOperation.getResultDescription());
        } else {
            return new ResponseEntity<>(new AppError("ILLEGAL INPUT DATA", resultOperation.getResultDescription()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Вывод оценок пользователя по id фильма",
            description = "Позволяет вывести оценки пользователя по id фильма"
    )
    @GetMapping("grade_user_by_id_film")
    public RaitingDto gradeUserByIdFilm(@RequestParam Long userId, @RequestParam Long filmId) {
        return raitingConverter.entityToDto(raitingService.gradeUserByIdFilm(userId, filmId));
    }

    @Operation(
            summary = "Вывод средней оценки по всем пользователям по id фильма",
            description = "Позволяет среднюю оценку пользователей по id фильма"
    )
    @GetMapping("total_film_raiting")
    public String totalRaitingFilmById(@RequestParam Long filmId) {
        return String.format("%.2f",raitingService.getTotalGrade(filmId));
    }
}
