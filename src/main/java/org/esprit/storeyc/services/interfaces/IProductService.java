package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.dto.UserDto;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.entities.User;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    void createProduct(Product productDto) ;
    ProductDto createProductAndAssignToCategory(ProductDto productDto, Integer categoryId);
    void updateProduct(ProductDto updatedProductDto) ;
    void deleteProduct(Integer productId);
    ProductDto getProductById(Integer productId);
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProductsByName(String name);
    Product getRandomProduct(List<Product> products) ;
    List<ProductDto> getRandomProducts(int count) ;

    //the Management of promotions/offers
    List<ProductDto> getProductsByPromotion(String promotionName);
    void addPromotionToProduct(Integer productId, String promotionName);
    void removePromotionFromProduct(Integer productId) ;
    void applyDiscountToProduct(Integer productId, Integer discount) ;
    public void applyPercentageDiscountToProduct(Integer productId, float percentageDiscount) ;



    // Management of loyalty points
    void addLoyaltyPointsToUser(Integer userId, Float points);
    void subtractLoyaltyPointsFromUser(Integer userId, Float points);

    //Management of tool sales/rentals:
    List<ProductDto> getAllRentals();
    List<ProductDto> getAllSales();



}
