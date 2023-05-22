package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.RatingDto;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Rating;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.RatingRepository;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final FilmService filmService;

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public ResultOperation addFilmRating(RatingDto ratingDto) {
        ResultOperation resultOperation = new ResultOperation();
        if (ratingDto.getUser_id() == null) {
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Невозможно создать оценку и отзыв. Анонимный пользователь не может оценивать фильм");
        } else if (ratingDto.getGrade() < 1 || ratingDto.getGrade() > 5) {
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Оценка не может быть меньше 1 и больше 5");
        } else {
            Rating rating = new Rating();
            Film film = filmService.findById(ratingDto.getFilm_id());
            Optional<Rating> tempRaiting = ratingRepository.findRatingByFilmAndUserId(film, ratingDto.getUser_id());
            if (tempRaiting.isPresent()) {
                resultOperation.setResult(false);
                resultOperation.setResultDescription("Невозможно создать оценку и отзыв. Пользователь уже оценивал фильм");
            } else {
                rating.setUserId(ratingDto.getUser_id());
                rating.setFilm(film);
                rating.setGrade(ratingDto.getGrade());
                if (ratingDto.getReview()==null){
                    rating.setReview("");
                }else{
                    rating.setReview(ratingDto.getReview());
                }
                rating.setCreatedBy("frontUser");
                ratingRepository.save(rating);
                resultOperation.setResult(true);
                resultOperation.setResultDescription("OK");
            }
        }
        return resultOperation;
    }
    public Rating gradeUserByIdFilm(Long userId, Long filmId){
        Film film=filmService.findById(filmId);
        return ratingRepository.findRatingByFilmAndUserId(film,userId).orElseThrow(()->new ResourceNotFoundException("Оценка и комментарий пользователя с id="+userId+
                                                                                                                            " для фильма с id="+filmId+" не найдены"));
    }
    public Double getTotalGrade(Long filmId){
        Double result= ratingRepository.getTotalGrade(filmId);
        if (result!=null){
            return result;
        }else{
            return 0.0;
        }
    }

    public List<Rating> listAllGradeAndReviewsByFilmId(Long filmId){
        Film film=filmService.findById(filmId);
        return ratingRepository.findAllByFilm(film);
    }
}
