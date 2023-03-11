package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.dto.UserDto;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.entities.User;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    ProductDto createProductAndAssignToCategory(ProductDto productDto, Integer categoryId);
    void updateProduct(ProductDto updatedProductDto) ;
    void deleteProduct(Integer productId);
    ProductDto getProductById(Integer productId);
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProductsByName(String name);

        //the Management of promotions/offers
     List<ProductDto> getProductsByPromotion(String promotionName);
     void addPromotionToProduct(Integer productId, String promotionName);
     void removePromotionFromProduct(Integer productId) ;
     void applyDiscountToProduct(Integer productId, BigDecimal discount) ;
     void applyPercentageDiscountToProduct(Integer productId, BigDecimal percentageDiscount) ;
//  todo  applyPromotionDiscount.



    // Management of loyalty points
    void addLoyaltyPointsToUser(Integer userId, Float points);
    void subtractLoyaltyPointsFromUser(Integer userId, Float points);

    //Management of tool sales/rentals:
    List<ProductDto> getAllRentals();
    List<ProductDto> getAllSales();



}
