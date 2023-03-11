package org.esprit.storeyc.dto;

import lombok.*;
import org.esprit.storeyc.entities.Rating;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private Integer id;
    private int note;
    private String commentaire;
    private LocalDate dateNotation;
    private boolean estValide;
    private UserDto userDto;

    public static RatingDto fromEntity(Rating rating) {
        if (rating == null) {
            return null;
        }
        return RatingDto.builder()
                .id(rating.getId())
                .note(rating.getNote())
                .commentaire(rating.getCommentaire())
                .dateNotation(rating.getDate_notation())
                .estValide(rating.isEst_valide())
                .userDto(UserDto.fromEntity(rating.getUser()))
                .build();
    }

    public static Rating toEntity(RatingDto ratingDto) {
        if (ratingDto == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(ratingDto.getId());
        rating.setNote(ratingDto.getNote());
        rating.setCommentaire(ratingDto.getCommentaire());
        rating.setDate_notation(ratingDto.getDateNotation());
        rating.setEst_valide(ratingDto.isEstValide());
        return rating;
    }
}
