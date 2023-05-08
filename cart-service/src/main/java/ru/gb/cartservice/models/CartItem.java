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
    private int price;
    private boolean isSale;




    public CartItem(CartItemDto cartItemDto) { // (FilmDto filmdto)
        this.filmId = cartItemDto.getFilmId();
        this.filmTitle = cartItemDto.getTitle();
        this.filmImageUrlLink = cartItemDto.getImageUrlLink();
        this.price = cartItemDto.getPrice();

    }


}
