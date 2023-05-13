package ru.gb.api.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private String title;
    private String imageUrlLink;
    private int pricePerFilm;
    private int price;


    public CartItemDto(Long filmId, String filmTitle, String filmImageUrlLink, int quantity, int pricePerFilm, int price) {
    }
}



