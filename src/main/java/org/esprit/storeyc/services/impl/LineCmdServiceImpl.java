package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.dto.LineCmdDto;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.repositories.CommandRepository;
import org.esprit.storeyc.repositories.LineCmdRepository;
import org.esprit.storeyc.repositories.ProductRepository;
import org.esprit.storeyc.services.interfaces.ILineCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LineCmdServiceImpl implements ILineCmdService {

    @Autowired
    private LineCmdRepository lineCmdRepository;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public LineCmdDto createLineCmd(LineCmdDto lineCmdDto) {
        log.info("Creating new LineCmd: {}", lineCmdDto);

        LineCmd lineCmd = new LineCmd();
        lineCmd.setQuantite(lineCmdDto.getQuantite());
        lineCmd.setPrixUnitaire(lineCmdDto.getPrixUnitaire());

        Optional<Command> optionalCommand = commandRepository.findById(lineCmdDto.getId());
        if (optionalCommand.isPresent()) {
            lineCmd.setCommand(optionalCommand.get());
        } else {
            log.warn("Command with id {} not found, cannot create LineCmd", lineCmdDto.getId());
            return null;
        }

        Optional<Product> optionalProduct = productRepository.findById(lineCmdDto.getId());
        if (optionalProduct.isPresent()) {
            lineCmd.setProduct(optionalProduct.get());
        } else {
            log.warn("Product with id {} not found, cannot create LineCmd", lineCmdDto.getId());
            return null;
        }

        lineCmdRepository.save(lineCmd);

        LineCmdDto createdLineCmdDto = new LineCmdDto();
        createdLineCmdDto.setId(lineCmd.getId());
        createdLineCmdDto.setQuantite(lineCmd.getQuantite());
        createdLineCmdDto.setPrixUnitaire(lineCmd.getPrixUnitaire());
        createdLineCmdDto.setId(lineCmd.getCommand().getCommandeNumber());
        createdLineCmdDto.setId(lineCmd.getProduct().getProductId());

        log.info("New LineCmd created: {}", createdLineCmdDto);

        return createdLineCmdDto;
    }
}

