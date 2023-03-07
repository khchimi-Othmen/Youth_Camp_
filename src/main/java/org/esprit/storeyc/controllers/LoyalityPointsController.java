package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.services.impl.CommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Loyality Points Management")
@RestController
@RequestMapping("/LoyalityPoints")
public class LoyalityPointsController {
    @Autowired
    private CommandServiceImpl commandService;

    @PostMapping("/redeemPointsForDiscount/{commandId}/{pointsToRedeem}")
    public String redeemPointsForDiscount(@PathVariable Integer commandId, @PathVariable Integer pointsToRedeem){
        return commandService.redeemPointsForDiscount(commandId,pointsToRedeem);
    }
}
