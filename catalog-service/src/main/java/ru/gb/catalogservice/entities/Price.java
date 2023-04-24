package ru.gb.catalogservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;

@Entity
@Table(name="prices")
@Data
@RequiredArgsConstructor
public class Price extends GenericEntity {
    @Column(name="price_rent")
    private int priceRent;

    @Column(name="price_sale")
    private int priceSale;

}
