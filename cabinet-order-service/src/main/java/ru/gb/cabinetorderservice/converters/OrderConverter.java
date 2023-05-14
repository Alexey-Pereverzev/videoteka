package ru.gb.cabinetorderservice.converters;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.exceptions.ResourceNotFoundException;
import ru.gb.api.dtos.order.OrderDto;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.FilmServiceIntegration;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private FilmServiceIntegration filmServiceIntegration;


    public Order dtoToEntity(SpringDataJaxb.OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setUserId(order.getUserId());
        out.setFilmId(order.getFilmId());
        out.setPrice(order.getPrice());
        out.setSale(order.getType().equals("SALE"));
        Long filmId = order.getFilmId();
        FilmDto filmDto = filmServiceIntegration.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("К сожалению  фильм был удален  id: " + filmId));
        out.setFilmTitle(filmDto.getTitle());
        out.setDescription(filmDto.getDescription());
        out.setImageUrlLink(filmDto.getImageUrlLink());
        out.setRentStart(order.getRentStart());
        out.setRentEnd(order.getRentEnd());
        return out;
    }
}