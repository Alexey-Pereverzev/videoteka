package ru.gb.catalogservice.entities;

import lombok.Data;
import ru.gb.common.generic.entities.GenericEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="countries")
@Data
public class Country extends GenericEntity {
    @Column(name="title")
    private String title;
}
