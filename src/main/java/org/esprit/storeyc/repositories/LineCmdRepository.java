package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineCmdRepository extends JpaRepository<LineCmd, Integer> {
    List<LineCmd> findByCommand(Command command);

}
