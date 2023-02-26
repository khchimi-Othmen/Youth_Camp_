package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.UserDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {
    public static List<String> validate(UserDto userDto ) {
        List<String> errors = new ArrayList<>();
        if(userDto==null){
            errors.add("Veullez renseigner le nom ");
            errors.add("Veullez renseigner le prenom ");
            errors.add("Veullez renseigner l'email ");
            errors.add("Veullez renseigner le mot de passe ");
            return errors;
        }
        if(!StringUtils.hasLength(userDto.getNom())){
            errors.add("Veullez renseigner le nom ");
        }
        if(!StringUtils.hasLength(userDto.getPrenom())){
            errors.add("Veullez renseigner le prenom ");
        }
        if(!StringUtils.hasLength(userDto.getEmail())){
            errors.add("Veullez renseigner l'email ");
        }
        if(!StringUtils.hasLength(userDto.getMotDePasse())){
            errors.add("Veullez renseigner le mot de passe ");
        }
        return errors;
    }
}
