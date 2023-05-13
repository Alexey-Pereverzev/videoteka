package ru.gb.api.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long filmId;
    private String title;
    private String imageUrlLink;
    private int price;
    private boolean isSale;

}



