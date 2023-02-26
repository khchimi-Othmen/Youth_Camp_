package org.esprit.storeyc.validator;

import org.esprit.storeyc.dto.ProductDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductValidator {

    public static List<String> validate(ProductDto product) {
        List<String> errors = new ArrayList<>();
        if(product == null) {
            errors.add("Veuillez renseigner tous les champs");
            return errors;
        }
        if(!StringUtils.hasLength(product.getName())) {
            errors.add("Veuillez renseigner le nom du produit");
        }
        if(!StringUtils.hasLength(product.getDescription())) {
            errors.add("Veuillez renseigner la description du produit");
        }
        if(product.getPrice() == null) {
            errors.add("Veuillez renseigner le prix du produit");
        } else if(product.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            errors.add("Le prix du produit doit être supérieur à zéro");
        }
        if(!StringUtils.hasLength(product.getProducer())) {
            errors.add("Veuillez renseigner le nom du producteur");
        }
//        if(product.getCategory() == null) {
//            errors.add("Veuillez renseigner la catégorie du produit");
//        }
        return errors;
    }
}
