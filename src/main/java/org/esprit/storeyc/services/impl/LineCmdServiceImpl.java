package org.esprit.storeyc.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.repositories.LineCmdRepository;
import org.esprit.storeyc.repositories.ProductRepository;
import org.esprit.storeyc.services.interfaces.ILineCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public String updateQuantityAndTotal(Integer idLinecmd, Integer productId, Integer newQuantity, Integer nbrRentalPerDays) {
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










}
