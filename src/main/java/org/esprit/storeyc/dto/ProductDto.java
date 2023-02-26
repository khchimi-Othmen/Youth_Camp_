
package org.esprit.storeyc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.Product;
import java.math.BigDecimal;
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
    private String promotionName; // add promotionName attribute
    private Integer quantityAvailable; // add quantity available attribute
    private Boolean isRental; // add is rental attribute
    private BigDecimal rentalPrice; // add rental price attribute
    private Integer rentalDuration; // add rental duration attribute
    private Category category;

    @JsonIgnore
    private List<RatingDto> ratings;

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
                .promotionName(product.getPromotionName())
                //.ratings(ratingDtos)
                .quantityAvailable(product.getQuantityAvailable())
                .isRental(product.getIsRental())
                .rentalPrice(product.getRentalPrice())
                .rentalDuration(product.getRentalDuration())
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
        product.setPromotionName(productDto.getPromotionName());
        //        List<Rating> ratings = productDto.getRatings().stream()
//                .map(RatingDto::toEntity)
//                .collect(Collectors.toList());
//        product.setRatings(ratings);
        product.setQuantityAvailable(productDto.getQuantityAvailable());
        product.setIsRental(productDto.getIsRental());
        product.setRentalPrice(productDto.getRentalPrice());
        product.setRentalDuration(productDto.getRentalDuration());
        return product;
    }
}
