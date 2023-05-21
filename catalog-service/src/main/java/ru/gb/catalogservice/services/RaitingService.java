package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.RaitingDto;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Raiting;
import ru.gb.catalogservice.repositories.RaitingRepository;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RaitingService {
    private final RaitingRepository raitingRepository;
    private final FilmService filmService;

    public List<Raiting> findAll() {
        return raitingRepository.findAll();
    }

    public ResultOperation addFilmRating(RaitingDto raitingDto) {
        ResultOperation resultOperation = new ResultOperation();
        if (raitingDto.getUser_id() == null) {
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Невозможно создать оценку и отзыв. Анонимный пользователь не может оценивать фильм");
        } else if (raitingDto.getGrade() < 1 || raitingDto.getGrade() > 5) {
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Оценка не может быть меньше 1 и больше 5");
        } else {
            Raiting raiting = new Raiting();
            Film film = filmService.findById(raitingDto.getFilm_id());
            Optional<Raiting> tempRaiting = raitingRepository.findRaitingByFilmAndUserId(film, raitingDto.getUser_id());
            if (tempRaiting.isPresent()) {
                resultOperation.setResult(false);
                resultOperation.setResultDescription("Невозможно создать оценку и отзыв. Пользователь уже оценивал фильм");
            } else {
                raiting.setUserId(raitingDto.getUser_id());
                raiting.setFilm(film);
                raiting.setGrade(raitingDto.getGrade());
                if (raitingDto.getReview()==null){
                    raiting.setReview("");
                }else{
                    raiting.setReview(raitingDto.getReview());
                }
                raiting.setCreatedBy("frontUser");
                raitingRepository.save(raiting);
                resultOperation.setResult(true);
                resultOperation.setResultDescription("OK");
            }
        }
        return resultOperation;
    }
}
