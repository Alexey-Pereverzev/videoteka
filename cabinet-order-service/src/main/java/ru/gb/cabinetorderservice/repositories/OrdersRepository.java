package ru.gb.cabinetorderservice.repositories;


import net.bytebuddy.dynamic.DynamicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.cabinetorderservice.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.userId = ?1 and isDeleted = false")
    List<Order> findAllByUserId(long userId);
    @Query("select o from Order o where o.userId = ?1 and o.filmId = ?2")
    Optional<Order> findByUserIdAndFilmId(Long userId, Long filmId);


//    @Query("delete o from Order o where o.userId = ?1 and o.filmId = ?1")
//    void delete(Long userId, Long filmId);

}

