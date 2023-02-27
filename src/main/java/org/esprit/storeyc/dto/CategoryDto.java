package org.esprit.storeyc.dto;

import lombok.*;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer categoryId;
    private String name;
    private String description;

    private String categoryType;

    private List<Product> products;

    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }
        //todo
//        List<Integer> productIDs = category.getProducts()
//                .stream()
//                .map(Product::getProductId)
//                .collect(Collectors.toList());

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
//                .products(productIDs)
                .categoryType(category.getCategoryType())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setCategoryType(categoryDto.getCategoryType());
//
//        List<Product> products = categoryDto.getProducts()
//                .stream()
//                .map(id -> {
//                    Product product = new Product();
//                    product.setProductId(id);
//                    return product;
//                })
//                .collect(Collectors.toList());
//        category.setProducts(products);
        return category;
    }
}
