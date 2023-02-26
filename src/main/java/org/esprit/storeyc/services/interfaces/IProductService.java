package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    ProductDto createProduct(ProductDto productDto);
    public void updateProduct(ProductDto updatedProductDto) ;
    void deleteProduct(Integer productId);
    ProductDto getProductById(Integer productId);
    List<ProductDto> getAllProducts();
//    List<ProductDto> filterProductsByCategory(CaType category);//searchProductsByType
    List<ProductDto> searchProductsByName(String name);

    //the Management of promotions/offers
     List<ProductDto> getProductsByPromotion(String promotionName);
     void addPromotionToProduct(Integer productId, String promotionName);
     void removePromotionFromProduct(Integer productId) ;

    // Management of loyalty points
    void addLoyaltyPointsToUser(Integer userId, Float points);
    void subtractLoyaltyPointsFromUser(Integer userId, Float points);

    //Management of tool sales/rentals:
    List<ProductDto> getAllRentals();
    List<ProductDto> getAllSales();
    ProductDto addRental(ProductDto productDto);
    ProductDto addSale(ProductDto productDto);
    void deleteRental(Integer rentalId);
    void deleteSale(Integer saleId);

    // methods for payment management

    void processPayment(Integer productId, BigDecimal amount);

}
