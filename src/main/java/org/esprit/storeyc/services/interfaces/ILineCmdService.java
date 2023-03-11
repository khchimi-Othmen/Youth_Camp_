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

     void assignCommandToLineCmd(Integer lineCmdId, Integer commandId) ;
     public String updateQuantityAndTotal(Integer idLinecmd, Integer productId, Integer newQuantity, Integer nbrRentalPerDays) ;
     BigDecimal getTotalForLineCmd(Integer lineCmdId) ;

     }


