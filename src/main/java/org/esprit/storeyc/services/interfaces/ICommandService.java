package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ICommandService {
    Command createCommand(CommandDto commandDto) ;
    String cancelCommand(Integer commandId) ;
    String cancelCommand() ;
    public void unassignAllLineCmdForCommand(Integer commandId) ;
    CommandDto updateCommand(CommandDto commandDto);
    void deleteCommand(Integer commandeNumber);
    CommandDto getCommandByRef(String ref) ;
    CommandDto  getCommandById(Integer commandeNumber);
    List<CommandDto> getAllCommands();
    public List<Command> getAllC() ;
    void assignCommandToUser(Integer commandId, Integer userId) ;
    void assignDeliveryToCommand(Integer deliveryId, Integer commandId) ;
    //    String calculateTotalCostPerCommand(Integer commandId) ;
//    String calculateTotalCostPerCommande(Integer commandId) ;
    Integer calculateTotalCostForPendingCommands() ;

    String redeemPointsForDiscount(Integer commandId, Integer pointsToRedeem) ;
    void addToCart(Integer userId, Integer productId, int quantity) ;
}
