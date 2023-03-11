package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.LineCmd;

public interface CmdLineRepository extends JpaRepository<LineCmd, Integer> {
}