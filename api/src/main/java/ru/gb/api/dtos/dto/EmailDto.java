package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    String email;
    String subject;
    String firstName;
    String message;
}
