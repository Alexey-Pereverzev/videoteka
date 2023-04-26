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
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Genre;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    //Page<Film> findAllByIsDeletedIsFalse(PageRequest pageRequest, Sort sort, Specification specification);
    //@Query("SELECT f FROM Film f WHERE (f.countries) IN =:strings")
   // Page<Film> findAllByCountries (PageRequest pageRequest);

    @Query("select f from Film f join f.countries c join f.directors d join f.genres g " +
            "where (c in :countries) and (d in :directors) and (g in :genres) and (f.premierYear=:premierYear)")
    Page<Film> findWithFilter(PageRequest pageRequest, List<Country> countries, List<Director> directors, List<Genre> genres,int premierYear);
//    @Query("select f from Film f where f.countries IN :countries")
//    Page<Film> findWithFilter(PageRequest pageRequest,List<Country> countries);

    //Page<Film> findAllByCountriesIn(PageRequest pageRequest,List<Country> countries);
}
