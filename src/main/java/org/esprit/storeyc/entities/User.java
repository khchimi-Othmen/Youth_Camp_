package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
    @NotNull
    @Column(name = "loyalty_pts")
    private Float loyaltyPts = 0.0f;
    @NotNull
    @Column(name = "discount")
    private Float discount = 0.0f;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate loyaltyPointsExpireDate;


    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<Command> commands;


//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<Likes_Dislikes> likesDislikes;
//

}
