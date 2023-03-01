package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;

import java.util.List;

public interface ICommandService {
     Command createCommand(CommandDto commandDto) ;
    public String cancelCommand(Integer commandId) ;
        CommandDto updateCommand(CommandDto commandDto);
    void deleteCommand(Integer commandeNumber);
    CommandDto  getCommandById(Integer commandeNumber);
    List<CommandDto> getAllCommands();

    public String finalizeCommand(Integer commandId) ;
}
