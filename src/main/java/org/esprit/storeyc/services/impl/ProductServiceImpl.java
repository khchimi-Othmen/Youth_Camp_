package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.LineCmd;
import org.esprit.storeyc.entities.User;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.repositories.CategoryRepository;
import org.esprit.storeyc.repositories.LineCmdRepository;
import org.esprit.storeyc.repositories.UserRepository;
import org.esprit.storeyc.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.repositories.ProductRepository;
import org.esprit.storeyc.services.interfaces.IProductService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LineCmdRepository lineCmdRepository;


    @Transactional
    public ProductDto createProductAndAssignToCategory(ProductDto productDto, Integer categoryId) {
        List<String> errors = ProductValidator.validate(productDto);
        if (!errors.isEmpty()) {
            log.error("Product is not valid: {}", errors);
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);

        Product product = ProductDto.toEntity(productDto);
        product.setCategory(category);

        Product createdProduct = productRepository.save(product);
        return ProductDto.fromEntity(createdProduct);
    }


    @Override
    public void updateProduct(ProductDto updatedProductDto) {
        List<String> errors = ProductValidator.validate(updatedProductDto);
        if (!errors.isEmpty()) {
            log.error("Product is not valid: {}", errors);
        }

        Optional<Product> optionalProduct = productRepository.findById(updatedProductDto.getProductId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updatedProductDto.getName());
            existingProduct.setPrice(updatedProductDto.getPrice());
            existingProduct.setDescription(updatedProductDto.getDescription());
            existingProduct.setCategory(updatedProductDto.getCategory());
            productRepository.save(existingProduct);
        } else {
            log.error("Product with ID {} not found", updatedProductDto.getProductId());
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        if (productId == null) {
            log.error("Invalid product ID");
            return;
        }

        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return ProductDto.fromEntity(product);
        } else {
            log.error("Product with id {} not found", productId);
            return null;
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductDto.fromEntity(product));
        }
        return productDtos;
    }


    @Override
    public List<ProductDto> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductDto.fromEntity(product));
        }
        return productDtos;
    }



    @Override
    public List<ProductDto> getProductsByPromotion(String promotionName) {
        List<Product> products = productRepository.findByPromotion(promotionName);
        return products.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
    //todo fix the dto
    @Override
    public void addPromotionToProduct(Integer productId, String promotionName) {
        // Get the product by id
        Product product = productRepository.findById(productId).get();

        // Update the product with the new promotion
        product.setPromotion(promotionName);

        // Save the updated product entity to the database
        productRepository.save(product);
    }


    @Override
    public void removePromotionFromProduct(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPromotion(null);
            productRepository.save(product);
        }
    }



    @Override
    public void applyDiscountToProduct(Integer productId, BigDecimal discount) {
        ProductDto productDto = getProductById(productId);
        BigDecimal currentPrice = productDto.getPrice();
        BigDecimal discountedPrice = currentPrice.subtract(discount);
        productDto.setPrice(discountedPrice);
        updateProduct(productDto);
    }
    @Override
    public void applyPercentageDiscountToProduct(Integer productId, BigDecimal percentageDiscount) {
        ProductDto productDto = getProductById(productId);
        BigDecimal currentPrice = productDto.getPrice();
        BigDecimal discountAmount = currentPrice.multiply(percentageDiscount.divide(BigDecimal.valueOf(100)));
        BigDecimal discountedPrice = currentPrice.subtract(discountAmount);
        productDto.setPrice(discountedPrice);
        updateProduct(productDto);
    }




    public void addLoyaltyPointsToUser(Integer userId, Float points) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Float currentPoints = user.getLoyaltyPts();
            user.setLoyaltyPts(currentPoints + points);
            userRepository.save(user);
        }
    }

    public void subtractLoyaltyPointsFromUser(Integer userId, Float points) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Float currentPoints = user.getLoyaltyPts();
            user.setLoyaltyPts(currentPoints - points);
            userRepository.save(user);
        }
    }

    @Override
    public List<ProductDto> getAllRentals() {
        List<Product> products = productRepository.findRentals();
        return products.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllSales() {
        List<Product> products = productRepository.findSales();
        return products.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}

