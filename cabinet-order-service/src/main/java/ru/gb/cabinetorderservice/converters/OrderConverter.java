package ru.gb.cabinetorderservice.converters;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import ru.gb.api.dtos.order.OrderDto;
import ru.gb.cabinetorderservice.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {


    public Order dtoToEntity(SpringDataJaxb.OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setUserId(order.getUserId());
        out.setFilmId(order.getFilmId());
        out.setPrice(order.getPrice());
        out.setRentStart(order.getRentStart());
        out.setRentEnd(order.getRentEnd());
        return out;
    }
}