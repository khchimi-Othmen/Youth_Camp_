package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.RatingDto;

public interface IRatingService {
    void createRating(RatingDto ratingDto);
    void updateRating(RatingDto ratingDto);
    void deleteRating(Long id);
}