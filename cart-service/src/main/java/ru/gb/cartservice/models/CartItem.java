package ru.gb.cartservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.cart.CartItemDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long filmId;
    private String filmTitle;
    private String filmImageUrlLink;
    private int quantity;
    private int pricePerFilm;
    private int price;



    public CartItem(CartItemDto cartItemDto) { // (FilmDto filmdto)
        this.filmId = cartItemDto.getId();
        this.filmTitle = cartItemDto.getTitle();
        this.filmImageUrlLink = cartItemDto.getImageUrlLink();
        this.quantity = 1;
        this.pricePerFilm = cartItemDto.getPrice();
        this.price = cartItemDto.getPrice();
    }


}
