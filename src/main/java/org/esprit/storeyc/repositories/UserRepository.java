package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByLoyaltyPointsExpireDateBefore(LocalDate expirationDate);
}