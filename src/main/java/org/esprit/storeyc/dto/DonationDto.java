package org.esprit.storeyc.dto;

import lombok.*;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.Donation;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationDto {

    private Integer id;
    private double montant;
    private LocalDate date_donation;
    private List<Integer> commandIds;

    public static DonationDto fromEntity(Donation donation) {
        if (donation == null) {
            return null;
        }
        List<Integer> commandIds = donation.getCommands()
                .stream()
                .map(Command::getCommandeNumber)
                .collect(Collectors.toList());
        return DonationDto.builder()
                .id(donation.getId())
                .montant(donation.getMontant())
                .date_donation(donation.getDate_donation())
                .commandIds(commandIds)
                .build();
    }

    public static Donation toEntity(DonationDto donationDto) {
        if(donationDto == null){
            return null;
        }

        Donation donation = new Donation();

        donation.setId(donationDto.getId());
        donation.setMontant(donationDto.getMontant());
        donation.setDate_donation(donationDto.getDate_donation());
        List<Command> commands = donationDto.getCommandIds()
                .stream()
                .map(id -> {
                    Command command = new Command();
                    command.setCommandeNumber(id);
                    return command;
                })
                .collect(Collectors.toList());
        donation.setCommands(commands);
        return donation;
    }
}
