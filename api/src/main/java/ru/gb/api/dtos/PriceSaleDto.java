package ru.gb.api.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceSaleDto {
    private Long id;
    private int priceSale;
}
