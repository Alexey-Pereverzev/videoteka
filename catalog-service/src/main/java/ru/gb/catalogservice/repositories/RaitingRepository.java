package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Raiting;

import java.util.List;

@Repository
public interface RaitingRepository extends JpaRepository<Raiting, Long>{

}
