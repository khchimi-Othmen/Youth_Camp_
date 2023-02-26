package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.services.interfaces.ICommandService;
import org.esprit.storeyc.validator.CommandValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommandServiceImpl implements ICommandService {

        @Autowired
        private CommandRepository commandRepository;


    @Override
    public CommandDto createCommand(CommandDto commandDto) {
        List<String> errors = CommandValidator.validate(commandDto);
        if (!errors.isEmpty()) {
            log.error("Command is not valid: {}", errors);
        }

        Command createdCommand = commandRepository.save(CommandDto.toEntity(commandDto));
        return CommandDto.fromEntity(createdCommand);
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
}



