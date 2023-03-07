package org.esprit.storeyc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String producer;
    private Boolean available;
    @Column(nullable=true)
    private String promotion;
    private Integer quantityAvailable;
    private Boolean isRental ;// todo make it false

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Rating> ratings;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<LineCmd> lineCmds;



    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;


}
