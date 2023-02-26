package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.Command;

public interface CommandRepository extends JpaRepository<Command, Integer> {
}