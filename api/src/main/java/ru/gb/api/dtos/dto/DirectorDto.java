package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    private String firstName;
    private String lastName;
}
