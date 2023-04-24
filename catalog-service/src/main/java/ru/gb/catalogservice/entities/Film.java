package ru.gb.catalogservice.entities;

import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="films")
public class Film extends GenericEntity {
    @Column(name = "title")
    private String title;
    @Column(name="premier_year")
    private int premierYear;

    @Column(name = "image_url_link")
    private String imageUrlLink;

    @ManyToMany
    @JoinTable(name="countries_films",
            joinColumns=@JoinColumn(name="film_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private List<Country> countries;

    @ManyToMany
    @JoinTable(name="directors_films",
            joinColumns=@JoinColumn(name="film_id"),
            inverseJoinColumns=@JoinColumn(name="director_id"))
    private List<Director> directors;

    @ManyToMany
    @JoinTable(name="genres_films",
            joinColumns=@JoinColumn(name="film_id"),
            inverseJoinColumns=@JoinColumn(name="genre_id"))
    private List<Genre> genres;

    @Column(name="description")
    private String description;
}
