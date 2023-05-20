package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.RaitingDto;
import ru.gb.catalogservice.entities.Raiting;


@Component
public class RaitingConverter {
    public RaitingDto entityToDto(Raiting raiting){
        RaitingDto raitingDto=RaitingDto.builder()
                .user_id(raiting.getUserId())
                .film_id(raiting.getFilm().getId())
                .grade(raiting.getGrade())
                .review(raiting.getReview())
                .build();
        return raitingDto;
    }
}
