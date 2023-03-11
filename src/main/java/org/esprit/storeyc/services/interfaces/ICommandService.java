package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ICommandService {
     Command createCommand(CommandDto commandDto) ;
    String cancelCommand(Integer commandId) ;
    CommandDto updateCommand(CommandDto commandDto);
    void deleteCommand(Integer commandeNumber);
    CommandDto  getCommandById(Integer commandeNumber);
    List<CommandDto> getAllCommands();

    void assignCommandToUser(Integer commandId, Integer userId) ;
    void assignDeliveryToCommand(Integer deliveryId, Integer commandId) ;
    String calculateTotalCostPerCommand(Integer commandId) ;
    String redeemPointsForDiscount(Integer commandId, Integer pointsToRedeem) ;
    String finalizeCommand(Integer commandId) ;

    }
