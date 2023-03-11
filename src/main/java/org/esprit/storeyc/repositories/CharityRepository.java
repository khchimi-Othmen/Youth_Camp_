package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CharityRepository extends JpaRepository<Charity, Integer> {
//    List<Charity> findByOrderByTotalDonationsAsc();

}