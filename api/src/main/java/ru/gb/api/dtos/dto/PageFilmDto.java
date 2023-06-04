package ru.gb.api.dtos.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageFilmDto {
    private int totalPages;
    private int number;
    private int size;
    private int totalElements;
    private List<FilmDto> contents;

    @Override
    public String toString() {
        return "PageFilmDto{" +
                "totalPages=" + totalPages +
                ", number=" + number +
                ", size=" + size +
                ", contents=" + contents +
                '}';
    }
}