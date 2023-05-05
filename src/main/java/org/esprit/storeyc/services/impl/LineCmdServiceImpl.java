package org.esprit.storeyc.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.dto.LineCmdDto;
import org.esprit.storeyc.entities.CmdType;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.repositories.LineCmdRepository;
import org.esprit.storeyc.repositories.ProductRepository;
import org.esprit.storeyc.services.interfaces.ILineCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Slf4j
public class LineCmdServiceImpl implements ILineCmdService {

    @Autowired
    private LineCmdRepository lineCmdRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommandRepository commandRepository;

    @Override
    public String createLineCmdAndAssignProduct(Integer productId, Integer quantite, Integer nbrRentalPerDays) {
        if (quantite < 0) {
            return "Quantity should be a positive integer.";
        }
        LineCmd lineCmd = new LineCmd();
        lineCmd.setQuantite(quantite);
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "Product not found.";
        } else if (!product.getIsRental() && nbrRentalPerDays > 1) {
            return "You can't assign this product because it is not for rental.";
        } else if (nbrRentalPerDays < 0) {
            return "Number of rental days cannot be negative.";
        } else {
            lineCmd.setNbrRentalPerDays(nbrRentalPerDays);
            lineCmd.setProduct(product);
            lineCmdRepository.save(lineCmd);
            return "LineCmd created and product assigned successfully.";
        }
    }


    @Override
    public void assignCommandToLineCmd(Integer lineCmdId, Integer commandId) {
        Optional<LineCmd> optionalLineCmd = lineCmdRepository.findById(lineCmdId);
        Optional<Command> optionalCommand = commandRepository.findById(commandId);
        if (optionalLineCmd.isPresent() && optionalCommand.isPresent()) {
            LineCmd lineCmd = optionalLineCmd.get();
            Command command = optionalCommand.get();
            lineCmd.setCommand(command);
            lineCmdRepository.save(lineCmd);
        } else {
            log.error("Line command or command not found.");
        }
    }

    @Override
    public List<LineCmd> getLineCmdsByCommandId(Integer commandId) {
        Optional<Command> optionalCommand = commandRepository.findById(commandId);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            return lineCmdRepository.findByCommand(command);
        } else {
            log.error("Command not found.");
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteLineCmd(Integer lineCmdId){
        lineCmdRepository.deleteById(lineCmdId);
    }
    @Override
    public Integer getLineCmdQuantity(Integer lineCmdId) {
        Optional<LineCmd> lineCmdOptional = lineCmdRepository.findById(lineCmdId);
        if(lineCmdOptional.isPresent()) {
            return lineCmdOptional.get().getQuantite();
        } else {
            throw new EntityNotFoundException("Line command not found with id: " + lineCmdId);
        }
    }
    @Override
    public LineCmdDto updateLineCmdQuantity(Integer lineCmdId, Integer quantity) {
        Optional<LineCmd> lineCmdOptional = lineCmdRepository.findById(lineCmdId);
        if(lineCmdOptional.isPresent()) {
            LineCmd lineCmd = lineCmdOptional.get();
            lineCmd.setQuantite(quantity);
            lineCmdRepository.save(lineCmd);
            return LineCmdDto.fromEntity(lineCmd);
        }
        throw new EntityNotFoundException("Line command not found with id: " + lineCmdId);
    }
    @Override
    public LineCmdDto updateLineCmdNbrRentalPerDays(Integer lineCmdId, Integer nbrRentalPerDays) {
        Optional<LineCmd> lineCmdOptional = lineCmdRepository.findById(lineCmdId);
        if (lineCmdOptional.isPresent()) {
            LineCmd lineCmd = lineCmdOptional.get();
            Product product = lineCmd.getProduct();
            if (product.getIsRental()) {
                lineCmd.setNbrRentalPerDays(nbrRentalPerDays);
                lineCmdRepository.save(lineCmd);
                return LineCmdDto.fromEntity(lineCmd);
            } else {
                throw new IllegalStateException("Cannot update rental days for a non-rental product.");
            }
        }
        throw new EntityNotFoundException("Line command not found with id: " + lineCmdId);
    }



    @Override
    public String updateQuantityAndTotal(Integer idLinecmd, Integer productId, Integer newQuantity, Integer nbrRentalPerDays) {
        if (newQuantity < 0) {
            return "Quantity should be a positive integer.";
        }
        LineCmd lineCmd = lineCmdRepository.findById(idLinecmd).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (lineCmd == null) {
            return "Line command not found with id " + idLinecmd;
        } else if (product == null) {
            return "Product not found with id " + productId;
        } else if (nbrRentalPerDays != null && nbrRentalPerDays < 0) {
            return "Number of rental days cannot be negative.";
        } else {
            BigDecimal rentalPrice = product.getPrice();
            BigDecimal total = rentalPrice.multiply(BigDecimal.valueOf(nbrRentalPerDays == null ? 1 : nbrRentalPerDays));
            total = total.multiply(BigDecimal.valueOf(newQuantity));

            lineCmd.setProduct(product);
            lineCmd.setQuantite(newQuantity);
            lineCmd.setNbrRentalPerDays(nbrRentalPerDays == null ? 1 : nbrRentalPerDays);
            lineCmd.setTotal(total);

            lineCmdRepository.save(lineCmd);
            return "Line command updated successfully.";
        }
    }


    @Override
    public BigDecimal getTotalForLineCmd(Integer lineCmdId) {
        LineCmd lineCmd = lineCmdRepository.findById(lineCmdId).orElse(null);
        Integer quantity = lineCmd.getQuantite();
        BigDecimal total;
        if (lineCmd.getProduct().getIsRental()) {
            Integer rentalDays = lineCmd.getNbrRentalPerDays();
            BigDecimal rentalPricePerDay = lineCmd.getProduct().getPrice();
            total = rentalPricePerDay.multiply(BigDecimal.valueOf(rentalDays)).multiply(BigDecimal.valueOf(quantity));
        } else {
            BigDecimal pricePerUnit = lineCmd.getProduct().getPrice();
            total = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
        }
        lineCmd.setTotal(total);
        lineCmdRepository.save(lineCmd);
        return total;
    }



    @Override
    public String getProductNameForLineCmd(Integer lineCmdId) {
        LineCmd lineCmd = lineCmdRepository.findById(lineCmdId).orElse(null);
        if (lineCmd == null || lineCmd.getProduct() == null) {
            return null;
        }
        return lineCmd.getProduct().getName();
    }



    @Override
    public List<LineCmd> getAllLineCmd() {
        return lineCmdRepository.findAll();
    }


    @Override
    public void assignLineCmdToCommand(Integer lineCmdId) {
        Optional<LineCmd> optionalLineCmd = lineCmdRepository.findById(lineCmdId);
        if (optionalLineCmd.isPresent()) {
            LineCmd lineCmd = optionalLineCmd.get();
            Command command = null;
            List<Command> pendingCommands = commandRepository.findByCommmandType(CmdType.PENDING);
            if (!pendingCommands.isEmpty()) {
                command = pendingCommands.get(0);
            } else {
                command = new Command();
                command.setCommmandType(CmdType.PENDING);
                commandRepository.save(command);
            }
            lineCmd.setCommand(command);
            lineCmdRepository.save(lineCmd);
        } else {
            log.error("Line command not found.");
        }
    }


}
