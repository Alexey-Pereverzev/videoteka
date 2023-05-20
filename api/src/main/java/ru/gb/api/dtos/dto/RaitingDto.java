package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaitingDto {
    private Long id;
    private Long user_id;
    private  Integer grade;
    private  String review;
}
