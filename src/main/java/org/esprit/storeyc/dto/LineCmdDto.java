package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.esprit.storeyc.entities.LineCmd;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineCmdDto {
    private Integer id;
    private Integer quantite;
    private BigDecimal total;
    private Integer nbrRentalPerDays;

//    @JsonIgnore
    private ProductDto productDto;
//    @JsonIgnore
    private CommandDto commandDto;
    public LineCmdDto(ProductDto productDto, Integer quantite, Integer nbrRentalPerDays) {
        this.productDto = productDto;
        this.quantite = quantite;
        this.nbrRentalPerDays = nbrRentalPerDays;
    }

    public static LineCmdDto fromEntity(LineCmd lineCmd){
        if(lineCmd == null){
            return null;
        }
        return LineCmdDto.builder()
                .id(lineCmd.getId())
                .quantite(lineCmd.getQuantite())
                .total(lineCmd.getTotal())
                .nbrRentalPerDays(lineCmd.getNbrRentalPerDays())
                .build();
    }

    public static LineCmd toEntity(LineCmdDto lineCmdDto){
        if(lineCmdDto==null){
            return null;
        }
        LineCmd lineCmd = new LineCmd();
        lineCmd.setId(lineCmdDto.getId());
        lineCmd.setQuantite(lineCmdDto.getQuantite());
        lineCmd.setTotal(lineCmdDto.getTotal());
        lineCmd.setNbrRentalPerDays(lineCmdDto.getNbrRentalPerDays());
        return lineCmd;
    }
}
