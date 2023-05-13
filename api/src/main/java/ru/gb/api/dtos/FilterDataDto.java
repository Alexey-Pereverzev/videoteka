package ru.gb.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDataDto {
    private String[] filterCountryList;
    private String[] filterDirectorList;
    private String[] filterGenreList;
    private int startPremierYear;
    private int endPremierYear;
    private boolean isSale;
    private String check;
    private int minPrice;
    private int maxPrice;

    private int maxPriceRent;
    private int maxPriceSale;
}
