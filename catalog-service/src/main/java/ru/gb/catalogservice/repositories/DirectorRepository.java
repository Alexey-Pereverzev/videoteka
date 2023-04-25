package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

}
