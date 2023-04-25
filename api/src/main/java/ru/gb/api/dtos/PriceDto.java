package ru.gb.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {
    private int minPriceSale=0;
    private int maxPriceSale=0;
    private int minPriceRent=0;
    private int maxPriceRent=0;
}
