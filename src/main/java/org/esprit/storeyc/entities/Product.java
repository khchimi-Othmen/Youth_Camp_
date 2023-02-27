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
    private Integer productId;//reference;
    private String name;
    private String description;
    private BigDecimal price;
    private String producer;
    private Boolean available;
    private String promotion; // add promotion attribute
//    private String promotionName;
    private Integer quantityAvailable; // to track how many of the product are currently available for sale/rental.
    private Boolean isRental; //  a boolean flag to indicate whether the product is available for rental or just for sale
    private BigDecimal rentalPrice; // the price of renting the product (if applicable).
    private Integer rentalDuration; //  the duration of the rental period (if applicable)

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
