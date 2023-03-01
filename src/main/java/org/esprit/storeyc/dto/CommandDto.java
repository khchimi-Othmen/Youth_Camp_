package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.esprit.storeyc.entities.CmdType;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.PMType;

import org.esprit.storeyc.entities.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandDto {
    private Integer commandeNumber;
    private PMType paymentMethod;
    private CmdType commandType;
    private BigDecimal weight;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;
//    private CommandStatus status;
    @JsonIgnore
    private User user;


    public static CommandDto fromEntity(Command command) {
        if (command == null) {
            return null;
        }
        return CommandDto.builder()
                .commandeNumber(command.getCommandeNumber())
                .paymentMethod(command.getPaymentMethod())
                .commandType(command.getCommmandType())
                .weight(command.getWeight())
                .deliveryDate(command.getDeliveryDate())
                .user(command.getUser())
//                .status(command.getStatus())
                .build();
    }

    public static Command toEntity(CommandDto commandDto) {
        if (commandDto == null) {
            return null;
        }
        Command command = new Command();
        command.setCommandeNumber(commandDto.getCommandeNumber());
        command.setPaymentMethod(commandDto.getPaymentMethod());
        command.setCommmandType(commandDto.getCommandType());
        command.setWeight(commandDto.getWeight());
        command.setDeliveryDate(commandDto.getDeliveryDate());
        command.setUser(commandDto.getUser());
//        command.setStatus(commandDto.getStatus());
        return command;
    }
}
