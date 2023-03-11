package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    private Float discount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate loyaltyPointsExpireDate;


    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<Command> commands;

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<Rating> ratings;

//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Likes_Dislikes> likesDislikes;
//

}
