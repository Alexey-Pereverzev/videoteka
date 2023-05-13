package ru.gb.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private int minPriceSale=0;
    private int maxPriceSale=0;
    private int minPriceRent=0;
    private int maxPriceRent=0;
}
