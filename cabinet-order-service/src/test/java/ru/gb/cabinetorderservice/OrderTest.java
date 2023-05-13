package ru.gb.cabinetorderservice;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.services.OrderService;
import ru.gb.cartservice.services.CartService;

//
// это значит что работаем в Spring Boot окружении
//(classes = CartService.class) - эта строчка означает что для работы этоготеста нужен только этот бин CartService - ускоряет работу
@SpringBootTest
public class OrderTest {
    // внедряем бинн CartService
    @Autowired
    private OrderService orderService;


    @MockBean
    private CartService cartService;


    @BeforeEach
    public void initCart() {
        cartService.clearCart("1");
    }
//

    @Test
    public void createOrder() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setFilmId(5L);
        cartItemDto.setTitle("X");
        cartItemDto.setImageUrlLink("test");
        cartItemDto.setPrice(100);
        cartItemDto.setSale(true);



        cartService.addToCart("test_cart1", 5l, "X", "test", 100, true);
        Order order = new Order();
        order.setUserId(1L);
        order.setFilmId(1L);
        order.setType("X");
        order.setPrice(100);
        order.setType("Rent");
        orderService.createOrder(1l);
        Assertions.assertEquals(1, orderService.findOrdersByUserId(1L));
    }
}
