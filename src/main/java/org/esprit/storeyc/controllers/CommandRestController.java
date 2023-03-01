package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.services.impl.CommandServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Command Management")
@RestController
@RequestMapping("/commands")
public class CommandRestController {

	private final CommandServiceImpl commandService;

	public CommandRestController(CommandServiceImpl commandService) {
		this.commandService = commandService;
	}
	@PostMapping("/create")
	public Command createCommand(@RequestBody CommandDto commandDto) {
		return  commandService.createCommand(commandDto);
	}

	@PutMapping("/cancelCommand/{commandId}")
	public String cancelCommand(@PathVariable Integer commandId) {
		return commandService.cancelCommand(commandId);
	}
//	@PostMapping("/create")
//	public CommandDto createCommand(@RequestBody CommandDto commandDto) {
//		return commandService.createCommand(commandDto);
//	}

	@PutMapping("/updateCommand/{commandNumber}")
	public CommandDto updateCommand(@PathVariable Integer commandNumber, @RequestBody CommandDto commandDto) {
		commandDto.setCommandeNumber(commandNumber);
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

	@GetMapping("/getAllCommands")
	public List<CommandDto> getAllCommands() {
		return commandService.getAllCommands();
	}
	@PostMapping("/commands/{commandId}/finalize")
	public String finalizeCommand(@PathVariable Integer commandId) {
		return commandService.finalizeCommand(commandId);
	}

}