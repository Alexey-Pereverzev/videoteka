package ru.gb.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private Long id;
    private String title;
    private Integer premierYear;
    private String description;
    private String imageUrlLink;
    private List<String> genre;
    private List<String> country;
    private List<String> director;
    private Integer rentPrice;
    private Integer salePrice;
}
