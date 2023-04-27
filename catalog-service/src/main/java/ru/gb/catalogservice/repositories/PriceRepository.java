package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.entities.Price;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAllByIsDeletedIsFalseAndPriceSaleBetweenAndPriceRentBetween(int minSalePrice,int maxSalePrice,
                                                                                int minRentPrice,int maxRentPrice);
    List<Price> findAllByIsDeletedIsFalse();
    List<Price> findAllByIsDeletedIsFalseAndPriceSaleBetween(int minSalePrice, int maxSalePrice);
    List<Price> findAllByIsDeletedIsFalseAndPriceRentBetween(int minRentPrice, int maxRentPrice);

}
