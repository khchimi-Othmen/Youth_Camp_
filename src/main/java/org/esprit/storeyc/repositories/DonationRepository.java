package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.Donation;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
}