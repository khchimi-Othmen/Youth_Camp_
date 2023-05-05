package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.CmdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.Command;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommandRepository extends JpaRepository<Command, Integer> {
    @Query("SELECT c FROM Command c WHERE c.ref = :ref")
    Optional<Command> findByRef(@Param("ref") String ref);

    List<Command> findByCommmandType(CmdType cmdType);
}
