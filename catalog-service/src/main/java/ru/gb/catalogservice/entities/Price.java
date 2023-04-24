package ru.gb.catalogservice.entities;

import lombok.Data;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;

@Entity
@Table(name="prices")
@Data
public class Price extends GenericEntity {
    @Column(name="price_rent")
    private int priceRent;

    @Column(name="price_sale")
    private int priceSale;

    @ManyToOne (cascade= CascadeType.ALL)
    @JoinColumn (name="film_id")
    private Film film;

}
