
package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.Product;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String producer;
    private Boolean available;
    private String promotion; // add promotion attribute
    private Integer quantityAvailable; // add quantity available attribute
    private Boolean isRental; // add is rental attribute

//    @JsonIgnore
    private Category category;



    public static ProductDto fromEntity(Product product) {
        if (product == null) {
            return null;
        }
        //        List<RatingDto> ratingDtos = product.getRatings().stream()
//                .map(RatingDto::fromEntity)
//                .collect(Collectors.toList());

        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .producer(product.getProducer())
                .available(product.getAvailable())
                .category(product.getCategory())
                .promotion(product.getPromotion())
                .quantityAvailable(product.getQuantityAvailable())
                .isRental(product.getIsRental())
                .build();
    }

    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setProducer(productDto.getProducer());
        product.setAvailable(productDto.getAvailable());
        product.setCategory(productDto.getCategory());
        product.setPromotion(productDto.getPromotion());
        product.setQuantityAvailable(productDto.getQuantityAvailable());
        product.setIsRental(productDto.getIsRental());
        return product;
    }
}
