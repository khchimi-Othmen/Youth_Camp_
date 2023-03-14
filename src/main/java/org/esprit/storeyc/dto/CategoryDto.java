package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String code; // add this property

    @JsonIgnore
    private List<Product> products;

    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .categoryType(category.getCategoryType())
                .code(category.getCode())
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
        category.setCode(categoryDto.getCode());
        return category;
    }
}
