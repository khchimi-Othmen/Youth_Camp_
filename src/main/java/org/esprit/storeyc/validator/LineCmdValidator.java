package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.LineCmdDto;

import java.util.ArrayList;
import java.util.List;

public class LineCmdValidator {

    public static List<String> validate(LineCmdDto lineCmdDto) {
        List<String> errors = new ArrayList<>();
        if(lineCmdDto==null){
            errors.add("Veuillez renseigner la commande à valider.");
            return errors;
        }
        if(lineCmdDto.getProductDto()==null){
            errors.add("Veuillez choisir le produit.");
        }
//        if(lineCmdDto.getQuantite() == null || lineCmdDto.getQuantite() < 1){
//            errors.add("Veuillez saisir une quantité valide.");
//        }
        return errors;
    }
}
