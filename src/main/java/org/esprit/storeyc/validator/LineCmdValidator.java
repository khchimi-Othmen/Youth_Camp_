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
        if(lineCmdDto.getNbrRentalPerDays() != null && !lineCmdDto.getProductDto().getIsRental()) {
            errors.add("Cannot specify number of rental days for non-rental product");
        }
//        if(lineCmdDto.getQuantite() == null || lineCmdDto.getQuantite() < 1){
//            errors.add("Veuillez saisir une quantité valide.");
//        }
        return errors;
    }

    public static String validateRentalStatus(boolean isRental, Integer nbrRentalPerDays) {
        if (!isRental && nbrRentalPerDays != null) {
            return "Product is not for rental, cannot specify number of rental days";
        }
        return null;
    }
}
