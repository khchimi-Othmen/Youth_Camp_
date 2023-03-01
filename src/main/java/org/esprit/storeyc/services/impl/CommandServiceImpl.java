package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.*;
import org.esprit.storeyc.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.services.interfaces.ICommandService;
import org.esprit.storeyc.validator.CommandValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommandServiceImpl implements ICommandService {
       @Autowired
        private CommandRepository commandRepository;


        @Autowired
        private ProductRepository productRepository;

//    @Override
//    public CommandDto createCommand(CommandDto commandDto) {
//        List<String> errors = CommandValidator.validate(commandDto);
//        if (!errors.isEmpty()) {
//            log.error("Command is not valid: {}", errors);
//        }
//        Command createdCommand = commandRepository.save(CommandDto.toEntity(commandDto));
//
////        // Add 10 loyalty points to the user who created the command
////        User user = createdCommand.getUser();
////        productService.addLoyaltyPointsToUser(user.getId(), 10.0f);
//
//        return CommandDto.fromEntity(createdCommand);
//    }

    @Override
    public Command createCommand(CommandDto commandDto) {
        List<String> errors = CommandValidator.validate(commandDto);
        if (!errors.isEmpty()) {
            log.error("Command is not valid: {}", errors);
            return null;
        }

//        User user = userRepository.findById(commandDto.getUser().getId()).orElse(null);
//        if (user == null) {
//            log.error("User not found");
//            return null;
//        }

        Command command = CommandDto.toEntity(commandDto);
//        command.setUser(user);
        command.setCommmandType(CmdType.PENDING); // set status to PENDING
        commandRepository.save(command);

        return command;
    }

    @Override
    public String cancelCommand(Integer commandId) {
        Command command = commandRepository.findById(commandId).orElse(null);
        if (command == null) {
            return "Command not found";
        }

        if (command.getCommmandType() == CmdType.CANCELLED) {
            return "Command has already been cancelled";
        }

        if (command.getCommmandType() == CmdType.CONFIRMED) {
            return "Command has already been finalized and cannot be cancelled";
        }

        if (command.getCommmandType() == CmdType.PENDING) {
            command.setCommmandType(CmdType.CANCELLED);
            commandRepository.save(command);
            return "Command cancelled";
        }

        return "Error cancelling command";
    }


    @Override
    public CommandDto updateCommand(CommandDto commandDto) {
        List<String> errors = CommandValidator.validate(commandDto);
        if (!errors.isEmpty()) {
            log.error("Command is not valid: {}", errors);
            return null;
        }

        Optional<Command> optionalCommand = commandRepository.findById(commandDto.getCommandeNumber());
        if (optionalCommand.isPresent()) {
            Command existingCommand = optionalCommand.get();
            existingCommand.setPaymentMethod(commandDto.getPaymentMethod());
            existingCommand.setCommmandType(commandDto.getCommandType());
            existingCommand.setWeight(commandDto.getWeight());
            existingCommand.setDeliveryDate(commandDto.getDeliveryDate());
            Command updatedCommand = commandRepository.save(existingCommand);
            return CommandDto.fromEntity(updatedCommand);
        } else {
            log.error("Command with ID {} not found", commandDto.getCommandeNumber());
            return null;
        }
    }

    @Override
    public void deleteCommand(Integer commandeNumber) {
        if (commandeNumber == null) {
            log.error("Invalid command ID");
            return;
        }

        commandRepository.deleteById(commandeNumber);
    }

    @Override
    public CommandDto getCommandById(Integer commandeNumber) {
        Optional<Command> optionalCommand = commandRepository.findById(commandeNumber);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            return CommandDto.fromEntity(command);
        } else {
            log.error("Command with id {} not found", commandeNumber);
            return null;
        }
    }

    @Override
    public List<CommandDto> getAllCommands() {
        List<Command> commands = commandRepository.findAll();
        List<CommandDto> commandDtos = new ArrayList<>();
        for (Command command : commands) {
            commandDtos.add(CommandDto.fromEntity(command));
        }
        return commandDtos;
    }
    /*Cette méthode vise à finaliser une commande en effectuant les opérations suivantes :

Récupérer la commande à partir de l'ID fourni en utilisant la méthode findById de commandRepository.
Si la commande existe, récupérer toutes les lignes de commande associées à cette commande.
Calculer le coût total de la commande en additionnant le coût total de chaque ligne de commande.
Définir l'état de la commande sur "CONFIRMED".*/
//    @Override
//    public String finalizeCommand(Integer commandId) {
//        Command command = commandRepository.findById(commandId).orElse(null);
//        if (command == null) {
//            return "Command not found";
//        }
//
//        List<LineCmd> lineCmds = command.getCommandLines();
//        BigDecimal total = BigDecimal.ZERO;
//        for (LineCmd lineCmd : lineCmds) {
//            total = total.add(lineCmd.getTotal());
//        }
//
//        command.setStatus(CommandStatus.CONFIRMED);
//        commandRepository.save(command);
//
//        return "Command finalized. Total cost: " + total.toString();
//    }

    @Override
    public String finalizeCommand(Integer commandId) {
        Command command = commandRepository.findById(commandId).orElse(null);
        if (command == null) {
            return "Command not found";
        }

        if (command.getCommmandType() == CmdType.CANCELLED) {
            return "Command has been cancelled";
        }

        if (command.getCommmandType() == CmdType.CONFIRMED) {
            return "Command has already been finalized";
        }

        if (command.getCommmandType() == CmdType.PENDING) {
            List<LineCmd> lineCmds = command.getCommandLines();
            BigDecimal total = BigDecimal.ZERO;
            for (LineCmd lineCmd : lineCmds) {
                Product product = lineCmd.getProduct();
                Integer availableQuantity = product.getQuantityAvailable();
                log.info("lmawjouda f stock"+availableQuantity);
                log.info("ly talebha client"+lineCmd.getQuantite());
                if (lineCmd.getQuantite() > availableQuantity) {
                    return "Product " + product.getName() + " error: available quantity "+availableQuantity+" only.";
                }
                product.setQuantityAvailable(availableQuantity - lineCmd.getQuantite());
                int x =availableQuantity - lineCmd.getQuantite();
                log.info("ba3ed ma na9ess"+x);
                productRepository.save(product);
                BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(lineCmd.getQuantite()));
                total = total.add(lineTotal);
                log.info("gdech f ligne lwa7da"+lineTotal);

            }

            command.setCommmandType(CmdType.CONFIRMED);
            commandRepository.save(command);
            log.info("========="+total);//gdech fil commande kamlla
            return "Command finalized. Total cost: " + total;
        }

        return "Error finalizing command";
    }





    /*Finalize Command: When all command lines are created and updated, finalize the command by updating its status and total price based on the sum of all command lines.*/
//    @Override
//    public void finalizeCommand(Integer commandId) {
//        Command command = commandRepository.findById(commandId).orElse(null);
//        if (command != null) {
//            BigDecimal total = BigDecimal.ZERO;
//            for (LineCmd lineCmd : command.getCommandLines()) {
//                BigDecimal lineTotal = lineCmd.getProduct().getPrice().multiply(lineCmd.getQuantite());
//                total = total.add(lineTotal);
//            }
//            command.setTotal(total);
//            command.setStatus(CommandStatus.valueOf("VALIDATED"));
//            commandRepository.save(command);
//        } else {
//            log.error("Failed to finalize command with id {}: invalid command id", commandId);
//        }
//    }


    }






