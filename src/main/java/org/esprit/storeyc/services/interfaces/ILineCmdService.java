package org.esprit.storeyc.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.esprit.storeyc.dto.LineCmdDto;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;

public interface ILineCmdService {

     LineCmd createLineCmdAndAssignProduct(Integer productId, Integer quantite) ;

     void assignCommandToLineCmd(Integer lineCmdId, Integer commandId) ;
//     void updateQuantityAndTotal(LineCmd lineCmd, BigDecimal quantity) ;
     BigDecimal getTotalForLineCmd(Integer lineCmdId) ;

     }


