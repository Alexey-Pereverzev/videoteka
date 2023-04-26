package ru.gb.cartservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.api.dtos.FilmDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long filmId;
    private String filmTitle;
    private int quantity;
    private int pricePerFilm;
    private int price;

    public CartItem(FilmDto filmDto) {
        this.filmId = filmDto.getId();
        this.filmTitle = filmDto.getTitle();
        this.quantity = 1;
        this.pricePerFilm = filmDto.getSalePrice();
        this.price = filmDto.getSalePrice();
    }


}
