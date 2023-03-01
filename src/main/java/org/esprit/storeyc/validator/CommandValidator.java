package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.CmdType;

import java.util.ArrayList;
import java.util.List;

public class CommandValidator {

    public static List<String> validate(CommandDto commandDto) {
        List<String> errors = new ArrayList<>();

        if (commandDto == null) {
            errors.add("Veuillez renseigner le mode de paiement de la commande");
//            errors.add("Veuillez renseigner le type de la commande");
            errors.add("Veuillez renseigner le poids de la commande");
            errors.add("Veuillez renseigner la date de livraison de la commande");
            return errors;
        }

        if (commandDto.getPaymentMethod() == null) {
            errors.add("Veuillez renseigner le mode de paiement de la commande");
        }

//        if (commandDto.getCommandeNumber() == null) {
//            errors.add("Veuillez renseigner le type de la commande");
//        }

        if (commandDto.getWeight() == null) {
            errors.add("Veuillez renseigner le poids de la commande");
        }

        if (commandDto.getDeliveryDate() == null) {
            errors.add("Veuillez renseigner la date de livraison de la commande");
        }

        return errors;
    }

    public static boolean validateStatus(CmdType status) {
        return status == CmdType.CREATED;
    }
}
