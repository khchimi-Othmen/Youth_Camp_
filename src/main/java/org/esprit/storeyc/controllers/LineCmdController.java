package org.esprit.storeyc.controllers;

import org.esprit.storeyc.dto.LineCmdDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.repositories.LineCmdRepository;
import org.esprit.storeyc.services.interfaces.ILineCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/line-cmds")
@CrossOrigin("*")

public class LineCmdController {

    @Autowired
    private ILineCmdService lineCmdService;
    @Autowired
    private LineCmdRepository lineCmdRepository;

    @PostMapping("/createLineCmdAndAssignProduct/{productId}/{quantite}/{nbrRentalPerDays}")
    public String createLineCmdAndAssignProduct( @PathVariable Integer productId,
                                                 @PathVariable Integer quantite,
                                                 @PathVariable Integer nbrRentalPerDays) {
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
    public String updateQuantityAndTotal(@PathVariable Integer idLinecmd,@PathVariable Integer productId,
                                         @PathVariable Integer newQuantity,
                                         @RequestParam(required = false, defaultValue = "1") Integer nbrRentalPerDays) {
        return lineCmdService.updateQuantityAndTotal(idLinecmd, productId, newQuantity, nbrRentalPerDays);
    }

    @GetMapping("/getLineCmdQuantity")
    public Integer getLineCmdQuantity(Integer lineCmdId) {
        return lineCmdService.getLineCmdQuantity(lineCmdId);
    }

    @PutMapping("/updateLineCmdQuantity/{lineCmdId}/{quantity}")
    public LineCmdDto updateLineCmdQuantity(@PathVariable Integer lineCmdId,@PathVariable Integer quantity) {
        return lineCmdService.updateLineCmdQuantity(lineCmdId,quantity);
    }
    @PutMapping("/updateLineCmdNbrRentalPerDays/{lineCmdId}/{nbrRentalDays}")
    public LineCmdDto updateLineCmdNbrRentalPerDays(@PathVariable Integer lineCmdId, @PathVariable Integer nbrRentalDays) {
        return lineCmdService.updateLineCmdNbrRentalPerDays(lineCmdId, nbrRentalDays);
    }



    @GetMapping("/getAllLineCmd")
    public List<LineCmd> getAllLineCmd() {
        return lineCmdService.getAllLineCmd();
    }

    @GetMapping("/getProductNameForLineCmd/{lineCmdId}")
    public String getProductNameForLineCmd(@PathVariable Integer lineCmdId) {
        return lineCmdService.getProductNameForLineCmd(lineCmdId);
    }

    @GetMapping("/getLineCmdByCommandId")
    public List<LineCmd> getLineCmdsByCommandId(Integer commandId) {
        return lineCmdService.getLineCmdsByCommandId(commandId);
    }
    @DeleteMapping("/deleteLineCmd")
    public void deleteLineCmd(Integer lineCmdId){
        lineCmdService.deleteLineCmd(lineCmdId);
    }
    @PutMapping("/updateLineCmd")
    public void updateLineCmd(@RequestBody LineCmdDto lineCmdDto) {
        LineCmd lineCmd = lineCmdRepository.findById(lineCmdDto.getId()).get();
        lineCmd.setQuantite(lineCmdDto.getQuantite()); // Set the new quantity value
        lineCmdRepository.save(lineCmd);
    }
    @PutMapping("/assignLineCmdToCommand/{lineCmdId}")
    public void assignLineCmdToCommand(@PathVariable Integer lineCmdId) {
        lineCmdService.assignLineCmdToCommand(lineCmdId);
    }


}
