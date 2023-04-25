package ru.gb.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilmDto {
    private Long id;
    private String title;
    private int premierYear;
    private String imageUrlLink;
    private List<String> genre;
    private List<String> country;
    private List<String> director;
    private int rentPrice;
    private int salePrice;
}
