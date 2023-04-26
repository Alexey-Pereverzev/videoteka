package ru.gb.catalogservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Film;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    //Page<Film> findAllByIsDeletedIsFalse(PageRequest pageRequest, Sort sort, Specification specification);
    //@Query("SELECT f FROM Film f WHERE (f.countries) IN =:strings")
   // Page<Film> findAllByCountries (PageRequest pageRequest);
    Page<Film> findAllByCountriesIn(PageRequest pageRequest,List<Country> countries);
}
