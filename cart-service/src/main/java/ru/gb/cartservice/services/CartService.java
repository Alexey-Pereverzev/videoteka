package ru.gb.cartservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.cartservice.integrations.FilmServiceIntegration;
import ru.gb.cartservice.models.Cart;
import ru.gb.cartservice.models.CartItem;


import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final FilmServiceIntegration filmServiceIntegration;

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

            c.add(cartItemDto, isSale);
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
// проверка корзины через интеграцию с бд фильмами перед оплатой
    // to do требует доработки по полю is_delete в FilmDto его нет
    public String validateCart(String cartKey) {
        String massege = "";
        Cart cart = getCurrentCart(cartKey) ;
        for (CartItem cart1 : cart.getItems()) {
            Long id = cart1.getFilmId();
            FilmDto filmDto = new FilmDto();
            //  FilmDto filmDto = filmServiceIntegration.findById(id).orElseThrow(() -> new ResourceNotFoundException("К сожалению  фильм был удален попробуйте снова оформить заказ .  id: " + cart1.getFilmTitle()));
            FilmDto filmDto1 = filmServiceIntegration.findById(id);
            if (!filmDto.equals(filmDto1)) {
                cart.remove(id);
                updateCart(cartKey, cart);
                massege = "Некоторые фильмы были удалены пожалуйста вернитесь в корзину и обновите страницу ";
            } else
                massege = "Можно оплавивать";


        }
        return massege;
    }

    public StringResponse redisContent() {
        Set<String> keys = redisTemplate.keys("*");
        String result = "";
        for (String key: keys){
           result = result + key + " ";
        }
        return new StringResponse(result);

    }
//     удаляем фильм из корзины
//    public void deletefilm (Long filmId){
//        // получем список ключей из корзины
//        Set<String> keys = redisTemplate.keys("*");
//        // перебираем ключи и проходимся по корзинам
//       for (String key: keys){
//           Cart cart = getCurrentCart(key);
//           List<CartItem> item = cart.getItems();
//           // получем список заказов и удаляем заказ
//           for (CartItem items:item){
//              if (items.getFilmId()== filmId){
//                  item.remove(items);
//              }
//           }
//
//       }

//    }


    }
