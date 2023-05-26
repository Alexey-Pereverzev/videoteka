package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private Long id;
    private String title;
}
