package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.api.dtos.dto.RaitingDto;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Raiting;


@Component
public class RaitingConverter {
    public RaitingDto entityToDto(Raiting raiting){
        RaitingDto raitingDto=RaitingDto.builder()
                .id(raiting.getId())
                .user_id(raiting.getUserId())
                .grade(raiting.getGrade())
                .review(raiting.getReview())
                .build();
        return raitingDto;
    }
}
