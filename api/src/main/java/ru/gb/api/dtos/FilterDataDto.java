package ru.gb.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FilterDataDto {
    private String[] filterCountryList;
    private String[] filterDirectorList;
    private String[] filterGenreList;
    private int startPremierYear;
    private int endPremierYear;
    private boolean isSale;
    private int minPrice;
    private int maxPrice;
}
