package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Raiting;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaitingRepository extends JpaRepository<Raiting, Long>{
    Optional<Raiting> findRaitingByFilmAndUserId(Film film, Long userId);

    @Query("select sum(r.grade)/1.0/count(r.grade) from Raiting r where r.film.id=:filmId")
    Double getTotalGrade(Long filmId);
}