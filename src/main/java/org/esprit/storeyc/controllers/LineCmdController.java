package org.esprit.storeyc.controllers;

import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.services.interfaces.ILineCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/line-cmds")
public class LineCmdController {

    @Autowired
    private ILineCmdService lineCmdService;

    @PostMapping("/createLineCmdAndAssignProduct/{productId}/{quantite}/{nbrRentalPerDays}")
    public LineCmd createLineCmdAndAssignProduct( @PathVariable Integer productId,
                                                  @PathVariable Integer quantite,
                                                  @RequestParam(required = false, defaultValue = "1") Integer nbrRentalPerDays) {
        return lineCmdService.createLineCmdAndAssignProduct(productId, quantite, nbrRentalPerDays);
    }

    @PutMapping("/{lineCmdId}/assignCommand/{commandId}")
    public void assignCommandToLineCmd(@PathVariable Integer lineCmdId, @PathVariable Integer commandId) {
        lineCmdService.assignCommandToLineCmd(lineCmdId, commandId);
    }

    @PostMapping("/getTotalForLineCmdAndSaveIt/{lineCmdId}")
    public BigDecimal getTotalForLineCmd(@PathVariable("lineCmdId") Integer lineCmdId) {
        return lineCmdService.getTotalForLineCmd(lineCmdId);
    }


    @PutMapping("/updateQuantityAndTotal/{idLinecmd}/{productId}/{newQuantity}/{nbrRentalPerDays}")
        public void updateQuantityAndTotal(@PathVariable Integer idLinecmd,@PathVariable Integer productId,
                                       @PathVariable Integer newQuantity,
                                       @RequestParam(required = false, defaultValue = "1") Integer nbrRentalPerDays) {
        lineCmdService.updateQuantityAndTotal(idLinecmd, productId, newQuantity, nbrRentalPerDays);
    }
}
