package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.esprit.storeyc.entities.Charity;
import org.esprit.storeyc.entities.Command;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharityDto {
    private Integer id;
    private String name;
    private String description;
    private String website;
    private String contactInformation;

//    private List<Integer> commandIds;

//    @JsonIgnore
    public static CharityDto fromEntity(Charity charity) {
        if (charity == null) {
            return null;
        }

        return CharityDto.builder()
                .id(charity.getId())
                .name(charity.getName())
                .description(charity.getDescription())
                .website(charity.getWebsite())
                .contactInformation(charity.getContactInformation())
                .build();
    }

    public static Charity toEntity(CharityDto charityDto) {
        if (charityDto == null) {
            return null;
        }
        Charity charity = new Charity();
        charity.setId(charityDto.getId());
        charity.setName(charityDto.getName());
        charity.setDescription(charityDto.getDescription());
        charity.setWebsite(charityDto.getWebsite());
        charity.setContactInformation(charityDto.getContactInformation());
        return charity;
    }
}
