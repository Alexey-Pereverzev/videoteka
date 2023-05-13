package ru.gb.cabinetorderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends GenericEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "type")
    private String type;

    @Column(name = "rent_start")
    private LocalDateTime rentStart;

    @Column(name = "rent_end")
    private LocalDateTime rentEnd;

}
