package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;

import java.util.List;

public interface ICommandService {
    CommandDto createCommand(CommandDto commandDto);
    CommandDto updateCommand(CommandDto commandDto);
    void deleteCommand(Integer commandeNumber);
    CommandDto  getCommandById(Integer commandeNumber);
    List<CommandDto> getAllCommands();

}
