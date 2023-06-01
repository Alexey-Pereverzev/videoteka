package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNameMailDto {
    private String firstName;
    private String email;
}

