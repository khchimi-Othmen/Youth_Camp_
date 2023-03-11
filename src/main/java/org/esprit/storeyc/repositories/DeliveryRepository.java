package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}