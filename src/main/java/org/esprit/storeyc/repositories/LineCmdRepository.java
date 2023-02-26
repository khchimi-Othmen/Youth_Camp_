package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.LineCmd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineCmdRepository extends JpaRepository<LineCmd, Integer> {
}