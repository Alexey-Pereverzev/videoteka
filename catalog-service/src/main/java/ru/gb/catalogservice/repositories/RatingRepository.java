package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
    Optional<Rating> findRatingByFilmAndUserId(Film film, Long userId);

    @Query("select sum(r.grade)/1.0/count(r.grade) from Rating r where r.film.id=:filmId")
    Double getTotalGrade(Long filmId);

    List<Rating> findAllByFilm(Film film);
}