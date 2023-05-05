package org.esprit.storeyc.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.esprit.storeyc.dto.LineCmdDto;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;

public interface ILineCmdService {

     String createLineCmdAndAssignProduct(Integer productId, Integer quantite,Integer nbrRentalPerDays) ;

     void deleteLineCmd(Integer lineCmdId);
     Integer getLineCmdQuantity(Integer lineCmdId) ;

     LineCmdDto updateLineCmdQuantity(Integer lineCmdId, Integer quantity) ;
     LineCmdDto updateLineCmdNbrRentalPerDays(Integer lineCmdId, Integer quantity) ;

     void assignCommandToLineCmd(Integer lineCmdId, Integer commandId) ;
     public List<LineCmd> getLineCmdsByCommandId(Integer commandId) ;
     String updateQuantityAndTotal(Integer idLinecmd, Integer productId, Integer newQuantity, Integer nbrRentalPerDays) ;
     BigDecimal getTotalForLineCmd(Integer lineCmdId) ;
     String getProductNameForLineCmd(Integer lineCmdId) ;
     List<LineCmd> getAllLineCmd() ;
     public void assignLineCmdToCommand(Integer lineCmdId) ;

}
