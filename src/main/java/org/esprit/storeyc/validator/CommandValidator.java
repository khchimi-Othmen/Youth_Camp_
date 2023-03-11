package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.CommandDto;
import org.esprit.storeyc.entities.CmdType;
import org.esprit.storeyc.entities.PMType;

import java.util.ArrayList;
import java.util.List;

public class CommandValidator {

    public static List<String> validate(CommandDto commandDto) {
        List<String> errors = new ArrayList<>();

        if (commandDto == null) {
            errors.add("CommandDto cannot be null");
            return errors;
        }

        if (commandDto.getPaymentMethod() == null) {
            errors.add("Payment method cannot be null");
        }

        if (commandDto.getWeight() == null) {
            errors.add("Weight cannot be null");
        }

        if (commandDto.getDeliveryDate() == null) {
            errors.add("Delivery date cannot be null");
        }

        return errors;
    }

    public static boolean validateStatus(CmdType status) {
        return status == CmdType.PENDING;
    }

    public static String validatePaymentMethod(PMType paymentMethod) {
        if (paymentMethod == PMType.CASH) {
            return null;
        } else {
            return "Only cash payments are currently accepted";
        }
    }
}
