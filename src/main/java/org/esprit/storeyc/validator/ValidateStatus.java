package org.esprit.storeyc.validator;

import org.esprit.storeyc.entities.CmdType;

public class ValidateStatus {
    public static boolean validateStatus(CmdType status) {
        return status == CmdType.CREATED;
    }

}
