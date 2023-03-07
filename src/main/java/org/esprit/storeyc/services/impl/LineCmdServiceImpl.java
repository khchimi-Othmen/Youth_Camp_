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
    public LineCmd createLineCmdAndAssignProduct(Integer productId, Integer quantite,Integer nbrRentalPerDays) {
        LineCmd lineCmd = new LineCmd();
        lineCmd.setQuantite(quantite);
        lineCmd.setNbrRentalPerDays(nbrRentalPerDays);
        Product product = productRepository.findById(productId).orElse(null);
        lineCmd.setProduct(product);
        lineCmdRepository.save(lineCmd);
        return lineCmd;
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
    public BigDecimal getTotalForLineCmd(Integer lineCmdId) {
        LineCmd lineCmd = lineCmdRepository.findById(lineCmdId).orElse(null);
        if (lineCmd == null) {
            throw new IllegalArgumentException("Invalid line command id: " + lineCmdId);
        }

        BigDecimal pricePerUnit = lineCmd.getProduct().getPrice();
        System.out.println("-----pricePerUnit"+pricePerUnit);
        BigDecimal quantity = BigDecimal.valueOf(lineCmd.getQuantite());
        System.out.println("-----quantity"+quantity);
        BigDecimal total = pricePerUnit.multiply(quantity);
        System.out.println("-----"+total);
        lineCmd.setTotal(total);
        lineCmdRepository.save(lineCmd);
        return total;
    }


    @Override
    public void updateQuantityAndTotal(Integer idLinecmd, Integer productId, Integer newQuantity, Integer nbrRentalPerDays) {
        LineCmd lineCmd = lineCmdRepository.findById(idLinecmd).orElseThrow(() -> new NoSuchElementException("LineCmd not found with id " + idLinecmd));
        Product product = productRepository.findById(productId).orElse(null);

        assert product != null;
        BigDecimal rentalPrice = product.getPrice();
        BigDecimal total = rentalPrice.multiply(BigDecimal.valueOf(nbrRentalPerDays == null ? 1 : nbrRentalPerDays));
        total = total.multiply(BigDecimal.valueOf(newQuantity));

        lineCmd.setProduct(product);
        lineCmd.setQuantite(newQuantity);
        lineCmd.setNbrRentalPerDays(nbrRentalPerDays == null ? 1 : nbrRentalPerDays);
        lineCmd.setTotal(total);

        lineCmdRepository.save(lineCmd);
    }






}
