package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.entities.User;
import org.esprit.storeyc.services.impl.CommandFinalizerServiceImpl;
import org.esprit.storeyc.services.impl.CommandServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Tag(name = "Command Management")
@RestController
@RequestMapping("/commands")
@CrossOrigin("*")
public class CommandRestController {

	private final CommandServiceImpl commandService;
	private final CommandFinalizerServiceImpl commandFinalizerService;

	public CommandRestController(CommandServiceImpl commandService, CommandFinalizerServiceImpl commandFinalizerService) {
		this.commandService = commandService;
		this.commandFinalizerService = commandFinalizerService;
	}

	@PostMapping("/create")
	public Command createCommand(@RequestBody CommandDto commandDto) {
		return  commandService.createCommand(commandDto);
	}

	@PutMapping("/cancelCommand/{commandId}")
	public String cancelCommand(@PathVariable Integer commandId) {
		return commandService.cancelCommand(commandId);
	}

	@PutMapping("/updateCommand")
	public CommandDto updateCommand(@RequestBody CommandDto commandDto) {
		return commandService.updateCommand(commandDto);
	}

	@DeleteMapping("/deleteCommand/{commandNumber}")
	public void deleteCommand(@PathVariable Integer commandNumber) {
		commandService.deleteCommand(commandNumber);
	}

	@GetMapping("/getCommandById/{commandNumber}")
	public CommandDto getCommandById(@PathVariable Integer commandNumber) {
		return commandService.getCommandById(commandNumber);
	}


	@GetMapping("/getCommandByRef/{ref}")
	public CommandDto getCommandByRef(@PathVariable String ref) {
		return commandService.getCommandByRef(ref);
	}

	@GetMapping("/getAllCommands")
	public List<CommandDto> getAllCommands() {
		return commandService.getAllCommands();
	}

	@GetMapping("/getAllC")
	public List<Command> getAllC() {
		return commandService.getAllC();
	}


	@PutMapping("/assignCommandToUser/{commandId}/{userId}")
	public void assignCommandToUser(@PathVariable Integer commandId,@PathVariable Integer userId) {
		commandService.assignCommandToUser(commandId,userId);
	}

	@PutMapping("/assignDeliveryToCommand/{deliveryId}/{commandId}")
	public void assignDeliveryToCommand(@PathVariable Integer deliveryId,@PathVariable Integer commandId) {
		commandService.assignDeliveryToCommand(deliveryId,commandId);

	}


	//		@PostMapping("/calculateTotalCostPerCommand/{commandId}")
//	public String calculateTotalCostPerCommand(@PathVariable Integer commandId) {
//		return commandService.calculateTotalCostPerCommand(commandId);
//
//	}
	@PostMapping("/calculateTotalCostPerCommand")
	public Integer calculateTotalCostForPendingCommands() {
		return commandService.calculateTotalCostForPendingCommands();

	}

	@PutMapping("/cancelCommand")
	public String cancelCommand() {
		return commandService.cancelCommand();
	}

	@PostMapping("/commandsfinalize")
	public String finalizeCommand() {
		return commandFinalizerService.finalizeCommand();
	}


	@PostMapping("/addToCart/{userId}/{productId}/{quantity}")
	public void addToCart(@PathVariable Integer userId,@PathVariable Integer productId,@PathVariable int quantity) {
		commandService.addToCart(userId, productId, quantity);
	}
	@PutMapping("/unassignAllLineCmdForCommand/{commandId}")
	public void unassignAllLineCmdForCommand(@PathVariable Integer commandId) {
		commandService.unassignAllLineCmdForCommand(commandId);
	}

}
