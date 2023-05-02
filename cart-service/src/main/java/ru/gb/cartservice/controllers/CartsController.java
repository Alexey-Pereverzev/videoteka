package ru.gb.cartservice.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.cartservice.converters.CartConverter;
import ru.gb.cartservice.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина" , description = "Методы работы с корзиной ")
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(username, uuid)));
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{filmId}/{filmTitle}/{filmImageUrlLink}/{filmPrice}")
    public void add(@RequestHeader(required = false) String username,  @RequestParam String uuid,  @RequestParam long filmId,  @RequestParam String filmTitle,  @RequestParam String filmImageUrlLink,  @RequestParam int filmPrice) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), filmId, filmTitle, filmImageUrlLink, filmPrice);
    }



    @GetMapping("/{uuid}/remove/{filmId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long filmId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), filmId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}
