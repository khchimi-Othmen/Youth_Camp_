package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.*;
import org.esprit.storeyc.repositories.*;
import org.esprit.storeyc.services.interfaces.ICommandService;
import org.esprit.storeyc.validator.CommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class CommandServiceImpl implements ICommandService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LineCmdRepository lineCmdRepository;
    public String generateRandomString(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

    @Override
    public Command createCommand(CommandDto commandDto) {
        List<String> errors = CommandValidator.validate(commandDto);

        String ref=this.generateRandomString(10);

        if (!errors.isEmpty()) {
            log.error("Command is not valid: {}", errors);
            return null;
        }
        Command command = CommandDto.toEntity(commandDto);
        command.setRef(ref);
        command.setCommmandType(CmdType.PENDING); // set status to PENDING
        commandRepository.save(command);

        return command;
    }

    @Override
    public String cancelCommand(Integer commandId) {
        Command command = commandRepository.findById(commandId).orElse(null);

        if (command == null) {
            return "Command not found";
        }

        if (command.getCommmandType() == CmdType.CANCELLED) {
            return "Command has already been cancelled";
        }

        if (command.getCommmandType() == CmdType.CONFIRMED) {
            return "Command has already been finalized and cannot be cancelled";
        }

        if (command.getCommmandType() == CmdType.PENDING) {
            command.setCommmandType(CmdType.CANCELLED);
            commandRepository.save(command);
            return "Command cancelled";
        }

        return "Error cancelling command";
    }

    @Override
    public String cancelCommand() {
        List<Command> pendingCommands = commandRepository.findByCommmandType(CmdType.PENDING);
        if (pendingCommands.isEmpty()) {
            return "No pending commands found";
        }

        Command command = pendingCommands.get(0);

        if (command.getCommmandType() == CmdType.CANCELLED) {
            return "Command has already been cancelled";
        }

        if (command.getCommmandType() == CmdType.CONFIRMED) {
            return "Command has already been finalized and cannot be cancelled";
        }

        if (command.getCommmandType() == CmdType.PENDING) {
            command.setCommmandType(CmdType.CANCELLED);
            commandRepository.save(command);
            unassignAllLineCmdForCommand(command.getCommandeNumber());
            return "Command cancelled";
        }

        return "Error cancelling command";
    }

    @Override
    public void unassignAllLineCmdForCommand(Integer commandId) {
        Optional<Command> optionalCommand = commandRepository.findById(commandId);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            List<LineCmd> lineCmds = lineCmdRepository.findByCommand(command);
            for (LineCmd lineCmd : lineCmds) {
                lineCmd.setCommand(null);
                lineCmdRepository.save(lineCmd);
            }
        } else {
            log.error("Command not found.");
        }
    }

    @Override
    public CommandDto updateCommand(CommandDto commandDto) {
        List<String> errors = CommandValidator.validate(commandDto);
        if (!errors.isEmpty()) {
            log.error("Command is not valid: {}", errors);
            return null;
        }

        List<Command> pendingCommands = commandRepository.findByCommmandType(CmdType.PENDING);
        if (pendingCommands.isEmpty()) {
            log.error("No pending command found");
            return null;
        }

        Command pendingCommand = pendingCommands.get(0);
        pendingCommand.setPaymentMethod(commandDto.getPaymentMethod());
        pendingCommand.setCommmandType(commandDto.getCommandType());
//        pendingCommand.setWeight(commandDto.getWeight());
        pendingCommand.setDeliveryDate(commandDto.getDeliveryDate());

        Command updatedCommand = commandRepository.save(pendingCommand);
        return CommandDto.fromEntity(updatedCommand);
    }


    @Override
    public void deleteCommand(Integer commandeNumber) {
        if (commandeNumber == null) {
            log.error("Invalid command ID");
            return;
        }

        commandRepository.deleteById(commandeNumber);
    }
    @Override
    public CommandDto getCommandByRef(String ref) {
        Optional<Command> optionalCommand = commandRepository.findByRef(ref);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            return CommandDto.fromEntity(command);
        } else {
            log.error("Command with ref {} not found", ref);
            return null;
        }
    }

    @Override
    public CommandDto getCommandById(Integer commandeNumber) {
        Optional<Command> optionalCommand = commandRepository.findById(commandeNumber);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            return CommandDto.fromEntity(command);
        } else {
            log.error("Command with id {} not found", commandeNumber);
            return null;
        }
    }
    @Override
    public List<CommandDto> getAllCommands() {
        List<Command> commands = commandRepository.findAll();
        List<CommandDto> commandDtos = new ArrayList<>();
        for (Command command : commands) {
            commandDtos.add(CommandDto.fromEntity(command));
        }
        return commandDtos;
    }
    @Override
    public List<Command> getAllC() {
        return commandRepository.findAll();
    }



    @Override
    public void assignCommandToUser(Integer commandId, Integer userId) {
        Command command = commandRepository.findById(commandId).orElseThrow(() -> new RuntimeException("Command not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        command.setUser(user);
        commandRepository.save(command);
    }

    @Override
    public void assignDeliveryToCommand(Integer deliveryId, Integer commandId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);
        Command command = commandRepository.findById(commandId).orElse(null);

        assert command != null;
        command.setDelivery(delivery);
        commandRepository.save(command);
    }

    /**calculateTotalCost before the redeem */
//    @Override
//    public String calculateTotalCostPerCommand(Integer commandId) {
//        Optional<Command> optionalCommand = commandRepository.findById(commandId);
//
//        if (!optionalCommand.isPresent()) {
//            return "Command not found";
//        }
//
//        Command command = optionalCommand.get();
//
//        BigDecimal total = calculateCommandTotal(command);
//
//        if (total.equals(BigDecimal.ZERO)) {
//            return "Error calculating total cost";
//        }
//
//        return "Total cost of command " + commandId + ": " + total;
//    }

    @Override
    public Integer calculateTotalCostForPendingCommands() {
        BigDecimal total = BigDecimal.ZERO;

        // Retrieve all pending commands
        List<Command> pendingCommands = commandRepository.findByCommmandType(CmdType.PENDING);

        // Calculate total cost for each command
        for (Command command : pendingCommands) {
            total = total.add(calculateCommandTotal(command));
        }

        return total.intValue();
    }



    private BigDecimal calculateCommandTotal(Command command) {
        BigDecimal total = BigDecimal.ZERO;

        for (LineCmd lineCmd : command.getCommandLines()) {
            Product product = lineCmd.getProduct();
            Integer requestedQuantity = lineCmd.getQuantite();
            BigDecimal lineTotal;

            if (product.getIsRental()) {
                Integer nbrRentalPerDays = lineCmd.getNbrRentalPerDays();
                BigDecimal rentalPricePerDay = product.getPrice();
                lineTotal = rentalPricePerDay.multiply(BigDecimal.valueOf(nbrRentalPerDays)).multiply(BigDecimal.valueOf(requestedQuantity));
            } else {
                lineTotal = product.getPrice().multiply(BigDecimal.valueOf(requestedQuantity));
            }

            total = total.add(lineTotal);
            lineCmd.setTotal(lineTotal);

            log.info("Total cost for this product line: " + lineTotal);
        }

        command.setTotalC(total);

        try {
            commandRepository.save(command);
        } catch (Exception e) {
            log.error("Error saving command " + command.getCommandeNumber() + " to the database", e);
            return BigDecimal.ZERO;
        }

        return total;
    }






    private final BigDecimal POINTS_TO_DINARS_CONVERSION_RATE = BigDecimal.valueOf(0.1);

    @Override
    public String redeemPointsForDiscount(Integer commandId, Integer pointsToRedeem) {
        Command command = commandRepository.findById(commandId).orElse(null);
        if (command == null) {
            return "Commande avec identifiant " + commandId + " introuvable";
        }

        User user = command.getUser();
        BigDecimal purchaseAmount = command.getTotalC();

        BigDecimal pointsWorth = BigDecimal.valueOf(pointsToRedeem).multiply(POINTS_TO_DINARS_CONVERSION_RATE);
        BigDecimal availablePointsWorth = BigDecimal.valueOf(user.getLoyaltyPts()).multiply(POINTS_TO_DINARS_CONVERSION_RATE);
        if (pointsWorth.compareTo(availablePointsWorth) > 0) {
            return "Pas assez de points de fidélité pour le rachat";
        }
        // check if points have expired
        if (user.getLoyaltyPointsExpireDate() != null && user.getLoyaltyPointsExpireDate().isBefore(LocalDate.now())) {
            return "Les points de fidélité ont expiré";
        }
        double discountPercentage = (double) pointsToRedeem * POINTS_TO_DINARS_CONVERSION_RATE.doubleValue() / command.getTotalC().doubleValue() * 100;
        BigDecimal discountAmount = command.getTotalC().multiply(BigDecimal.valueOf(discountPercentage / 100)).setScale(2, RoundingMode.HALF_UP);

        user.setLoyaltyPts(user.getLoyaltyPts() - pointsToRedeem);
        command.setDiscountAmount(discountAmount);
        command.setTotalC(purchaseAmount.subtract(discountAmount));
        userRepository.save(user);
        commandRepository.save(command);

        return "Points de fidélité rachetés avec succès pour une remise de "
                + discountPercentage + "% sur la commande d'identifiant " + commandId
                + ". Nouveau total de la commande : " + command.getTotalC();
    }

    //    public String getRefByLineCmdId(Integer lineCmdId) {
//        Optional<LineCmd> optionalLineCmd = commandLines.stream()
//                .filter(lineCmd -> lineCmd.getId().equals(lineCmdId))
//                .findFirst();
//
//        if (optionalLineCmd.isPresent()) {
//            LineCmd lineCmd = optionalLineCmd.get();
//            return lineCmd.getCommand().getRef();
//        } else {
//            return null;
//        }
//    }
    @Override
    public void addToCart(Integer userId, Integer productId, int quantity) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        Command cart = getOrCreateCart(user);
        LineCmd line = createLineItem(cart, product, quantity);
        cart.getCommandLines().add(line);
        updateCartTotal(cart);
    }

    private Command getOrCreateCart(User user) {
        Command cart = user.getCommands().stream()
                .filter(c -> c.getCommmandType() == CmdType.PENDING)
                .findFirst()
                .orElse(null);

        if (cart == null) {
            cart = new Command();
            cart.setUser(user);
            cart.setCommmandType(CmdType.PENDING);
            cart.setDeliveryDate(LocalDate.now().plusDays(7));
            cart.setTotalC(BigDecimal.ZERO);
            cart.setDiscountAmount(BigDecimal.ZERO);
            cart.setDonation(false);
            cart.setCommandLines(new ArrayList<>());
            user.getCommands().add(cart);
        }

        return cart;
    }

    private LineCmd createLineItem(Command cart, Product product, int quantity) {
        LineCmd line = new LineCmd();
        line.setCommand(cart);
        line.setProduct(product);
        line.setQuantite(quantity);
        line.setNbrRentalPerDays(0);
        line.setTotal(product.getPrice().multiply(new BigDecimal(quantity)));
        return line;
    }

    private void updateCartTotal(Command cart) {
        BigDecimal total = cart.getCommandLines().stream()
                .map(LineCmd::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalC(total);
    }

}




