package ru.gb.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectorDto {
    private Long id;
    private String firstName;
    private String lastName;
}
