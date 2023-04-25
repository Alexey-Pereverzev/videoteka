package ru.gb.catalogservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
