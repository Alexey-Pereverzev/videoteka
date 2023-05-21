package ru.gb.api.dtos.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaitingDto {
    private Long film_id;
    private Long user_id;
    private  Integer grade;
    private  String review;

    @Override
    public String toString() {
        return "RaitingDto{" +
                "id=" + film_id +
                ", user_id=" + user_id +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
