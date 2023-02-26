package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private String photo;
    private String email;
    private String motDePasse;
    private Float loyaltyPts;

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<Command> commands;

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<Rating> ratings;

}
