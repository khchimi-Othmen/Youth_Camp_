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
@ToString(exclude = {"donation", "delivery", "user", "commandLines"})
public class Command implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer commandeNumber;
    @Enumerated(EnumType.STRING)
    private PMType paymentMethod;
    @Enumerated(EnumType.STRING)
    private CmdType commmandType;//CommandStatus
    private BigDecimal weight;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;//duration<;howa ly ybda ya7sseb mnou date mta3 lkre
    private  BigDecimal totalC= BigDecimal.ZERO;
    @NotNull
    private BigDecimal discountAmount= BigDecimal.ZERO;
    private Boolean donation;
    private String ref=null;




    @OneToOne
    @JsonIgnore
    private Delivery delivery;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "command")
    @JsonIgnore
    private List<LineCmd> commandLines;

    /*The fetch attribute specifies that the relationship should be lazily loaded, which means that the Charity entity will be loaded only when needed.*/
    @ManyToOne
    @JsonIgnore
    private Charity charity;

}
