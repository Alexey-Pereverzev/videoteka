package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
