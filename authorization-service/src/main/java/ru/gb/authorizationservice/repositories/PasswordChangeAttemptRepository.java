package ru.gb.authorizationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.authorizationservice.entities.PasswordChangeAttempt;

import java.util.Optional;

@Repository
public interface PasswordChangeAttemptRepository extends JpaRepository<PasswordChangeAttempt, Long> {

}
