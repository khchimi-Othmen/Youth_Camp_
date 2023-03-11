package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.CharityDto;
import org.esprit.storeyc.services.impl.CharityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Charity Management")
@RestController
@RequestMapping("/charity")
public class CharityController {
    @Autowired
    private CharityServiceImpl charityService;

    @PostMapping("createCharity/")
    public CharityDto createCharity(@RequestBody CharityDto charityDto) {
        return charityService.createCharity(charityDto);
    }
    @PutMapping("updateCharity/{id}")
    public CharityDto updateCharity(@PathVariable Integer id, @RequestBody CharityDto charityDto) {
        return charityService.updateCharity(id,charityDto);
    }
    @Operation(summary = "Get all charities")
    @GetMapping
    public List<CharityDto> getAllCharities() {
        return charityService.getAllCharities();
    }
    @GetMapping("getCharityById/{id}")
    public CharityDto getCharityById(@PathVariable Integer id) {
        return charityService.getCharityById(id);
    }

    @DeleteMapping("deleteCharity/{id}")
    public void deleteCharity(@PathVariable Integer id) {
        charityService.deleteCharity(id);
    }

    @PutMapping("assignCommandToCharity/{idCommand}/{idCharity}")
    public String assignCharityToCommand(@PathVariable Integer idCommand,@PathVariable Integer idCharity) {
        return charityService.assignCharityToCommand(idCommand,idCharity);
    }
    @PutMapping("markCommandAsDonationBEFOREFINALIZ/{idCommand}")
    public String markCommandAsDonation(@PathVariable Integer idCommand) {
        return charityService.markCommandAsDonation(idCommand);
    }
}
