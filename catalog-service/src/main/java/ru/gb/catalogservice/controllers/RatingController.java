package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.RatingDto;
import ru.gb.catalogservice.converters.RatingConverter;
import ru.gb.catalogservice.exceptions.IllegalInputDataException;
import ru.gb.catalogservice.services.RatingService;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
@Tag(name = "Рейтинги", description = "Методы для работы с рейтингом фильмов")
public class RatingController {
    private final RatingService ratingService;
    private final RatingConverter ratingConverter;

    @Operation(
            summary = "Вывод таблицы оценок",
            description = "Позволяет вывести ВСЕ записи таблицы оценок и комментариев"
    )
    @GetMapping("list_all")
    public List<RatingDto> listAll() {
        return ratingService.findAll().stream().map(ratingConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Добавление оценки и комментария к фильму",
            description = "Позволяет добавить оценку и комментарий пользователя к фильму"
    )
    @PostMapping("/add_new")
    public ResponseEntity<?> addFilmRating(@RequestBody RatingDto ratingDto) {
        ResultOperation resultOperation = ratingService.addFilmRating(ratingDto);
        if (resultOperation.isResult()) {
            return ResponseEntity.ok().body(HttpStatus.OK + " " + resultOperation.getResultDescription());
        } else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }

    @Operation(
            summary = "Вывод оценки и отзыва пользователя по id фильма",
            description = "Позволяет вывести оценку и отзыв пользователя по id фильма"
    )
    @GetMapping("/grade_user_by_id_film")
    public RatingDto gradeUserByIdFilm(@RequestHeader String userId, @RequestParam Long filmId) {
        return ratingConverter.entityToDto(ratingService.gradeUserByIdFilm(Long.parseLong(userId), filmId));
    }

    @Operation(
            summary = "Вывод средней оценки по всем пользователям по id фильма",
            description = "Позволяет среднюю оценку пользователей по id фильма"
    )
    @GetMapping("/total_film_rating")
    public String totalRatingFilmById(@RequestParam Long filmId) {
        return String.format("%.2f", ratingService.getTotalGrade(filmId));
    }

    @Operation(
            summary = "Вывод отзывов по id фильма",
            description = "Позволяет получить список ВСЕХ отзывов и оценок по id фильма"
    )
    @GetMapping("/list_all_grade_and_review_by_filmId")
    public List<RatingDto> listAllGradeAndReviewByFilmId(@RequestParam Long filmId) {
        return ratingService.listAllGradeAndReviewsByFilmId(filmId).stream().map(ratingConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Вывод отзывов требующих модерации",
            description = "Позволяет получить список ВСЕХ отзывов требующих модерации"
    )
    @GetMapping("/list_all_grade_and_review_is_not_moderate")
    public List<RatingDto> listAllGradeAndReviewIsNotModerate() {
        return ratingService.listAllGradeAndReviewIsNotModerate().stream().map(ratingConverter::entityToDto).toList();
    }

    @GetMapping("/setModerateSuccess")
    public ResponseEntity<?> setModerateStatus(String userId,@RequestParam Long filmId) {
        ResultOperation resultOperation=ratingService.setModerateStatus(userId,filmId,true);
        if (resultOperation.isResult()) {
            return ResponseEntity.ok().body(HttpStatus.OK + " " + resultOperation.getResultDescription());
        } else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }

    @GetMapping("/setModerateRejected")
    public ResponseEntity<?> setModerateRejected(String userId,@RequestParam Long filmId) {
        ResultOperation resultOperation=ratingService.setModerateStatus(userId,filmId,false);
        if (resultOperation.isResult()) {
            return ResponseEntity.ok().body(HttpStatus.OK + " " + resultOperation.getResultDescription());
        } else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }
}
