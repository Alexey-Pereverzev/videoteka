package ru.gb.api.dtos.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppError {
    private String code;
    private String message;
}
