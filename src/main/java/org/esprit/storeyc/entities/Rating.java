package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private int note;

    private String commentaire;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_notation;
    private boolean est_valide;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private User user;




}
