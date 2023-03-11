package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validate(CategoryDto category) {
        List<String> errors = new ArrayList<>();
        if(category == null) {
            errors.add("Veuillez renseigner tous les champs");
            return errors;
        }
        if(!StringUtils.hasLength(category.getName())) {
            errors.add("Veuillez renseigner le nom de la catégorie");
        }
        if(!StringUtils.hasLength(category.getDescription())) {
            errors.add("Veuillez renseigner la description de la catégorie");
        }
        return errors;
    }
}
