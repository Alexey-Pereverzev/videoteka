package ru.gb.cartservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.cartservice.models.Cart;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<Object, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long filmId, String filmTitle, String filmImageUrlLink, int filmPrice, boolean isSale) {
        CartItemDto cartItemDto = new CartItemDto(filmId, filmTitle, filmImageUrlLink, filmPrice, isSale);
        execute(cartKey, c -> {
            c.add(cartItemDto);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long filmId) {
        execute(cartKey, c -> c.remove(filmId));
    }


    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }



}