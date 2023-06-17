package ru.gb.cartservice.models;


import lombok.Data;
import ru.gb.api.dtos.cart.CartItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }
// проверка если фильм есть корзине и статус в аренде и мы добавляем этот же фильм со статусом покупки то меняем цену и статус в корзине
    public void add(CartItemDto cartItemDto, boolean isSale) {
        if (add(cartItemDto.getFilmId())) {
            for (CartItem o : items) {
            if (!o.isSale() && isSale ){
                o.setSalePrice(cartItemDto.getSalePrice());
                o.setSale(true);

            }
            }

            return;
        }
        items.add(new CartItem(cartItemDto));
        recalculate();
    }

    public boolean add(Long id) {
        for (CartItem o : items) {
            if (Objects.equals(o.getFilmId(), id)) {
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
            if (o.isSale()) {
                totalPrice += o.getSalePrice();
            } else
                totalPrice += o.getRentPrice();
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