package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LineCmd implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer quantite;
    @NotNull
    private BigDecimal total= BigDecimal.ZERO;
    private Integer nbrRentalPerDays;



    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "commandeNumber")
    private Command command;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "productId")
    private Product product;



}
