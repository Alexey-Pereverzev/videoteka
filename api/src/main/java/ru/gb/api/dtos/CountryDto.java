package ru.gb.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDto {
    private Long id;
    private String title;
}
