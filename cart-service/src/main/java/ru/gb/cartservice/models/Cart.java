package ru.gb.cartservice.models;


import lombok.Data;
import ru.gb.api.dtos.FilmDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(FilmDto filmDto) {
        if (add(filmDto.getId())) {
            return;
        }
        items.add(new CartItem(filmDto));
        recalculate();
    }

    public boolean add(Long id) {
        for (CartItem o : items) {
            if (o.getFilmId().equals(id)) {
                return true;
            }
        }
        return false;
    }



    public void remove(Long filmId) {
        items.removeIf(o -> o.getFilmId().equals(filmId));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem o : items) {
            totalPrice += o.getPrice();
        }
    }

    public void merge(Cart another) {
        for (CartItem anotherItem : another.items) {
            boolean merged = false;
            for (CartItem myItem : items) {
                if (myItem.getFilmId().equals(anotherItem.getFilmId())) {
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
