package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.esprit.storeyc.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}