package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}