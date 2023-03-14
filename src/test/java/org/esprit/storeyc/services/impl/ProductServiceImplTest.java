//package org.esprit.storeyc.services.impl;
//
//import org.esprit.storeyc.dto.ProductDto;
//import org.esprit.storeyc.entities.Category;
//import org.esprit.storeyc.entities.Product;
//import org.esprit.storeyc.repositories.CategoryRepository;
//import org.esprit.storeyc.repositories.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//class ProductServiceImplTest {
//
//    @Mock
//    ProductRepository productRepository;
//
//    @Mock
//    CategoryRepository categoryRepository;
//
//    @InjectMocks
//    ProductServiceImpl productServiceImpl;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void createProductAndAssignToCategory() {
//        // Given
//        ProductDto productDto = new ProductDto();
//        productDto.setName("Product 1");
//        productDto.setDescription("Product 1 description");
//        productDto.setPrice(BigDecimal.TEN);
//
//        Category category = new Category();
//        category.setCategoryId(1);
//        category.setName("Category 1");
//
//        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
//        when(productRepository.save(any())).thenReturn(new Product());
//
//        // When
//        ProductDto createdProductDto = productServiceImpl.createProductAndAssignToCategory(productDto, 1);
//
//        // Then
//        Assertions.assertNotNull(createdProductDto);
//    }
//
//    @Test
//    void updateProduct() {
//        // Given
//        ProductDto productDto = new ProductDto();
//        productDto.setProductId(1);
//        productDto.setName("Product 1");
//        productDto.setDescription("Product 1 description");
//        productDto.setPrice(BigDecimal.TEN);
//
//        Product existingProduct = new Product();
//        existingProduct.setProductId(1);
//        existingProduct.setName("Product 1");
//        existingProduct.setDescription("Product 1 description");
//        existingProduct.setPrice(BigDecimal.ONE);
//
//        when(productRepository.findById(anyInt())).thenReturn(Optional.of(existingProduct));
//        when(productRepository.save(any())).thenReturn(new Product());
//
//        // When
//        productServiceImpl.updateProduct(productDto);
//
//        // Then
//        Assertions.assertEquals(productDto.getName(), existingProduct.getName());
//        Assertions.assertEquals(productDto.getDescription(), existingProduct.getDescription());
//        Assertions.assertEquals(productDto.getPrice(), existingProduct.getPrice());
//    }
//
//    @Test
//    void deleteProduct() {
//        // Given
//        Integer productId = 1;
//
//        // When
//        productServiceImpl.deleteProduct(productId);
//
//        // Then
//        // Verify that the repository's deleteById() method was called with the given product ID
//        Mockito.verify(productRepository).deleteById(productId);
//    }
//
//    @Test
//    void getProductById() {
//        // Given
//        Integer productId = 1;
//
//        Product existingProduct = new Product();
//        existingProduct.setProductId(productId);
//        existingProduct.setName("Product 1");
//        existingProduct.setDescription("Product 1 description");
//        existingProduct.setPrice(BigDecimal.TEN);
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
//
//        // When
//        ProductDto productDto = productServiceImpl.getProductById(productId);
//
//        // Then
//        Assertions.assertEquals(existingProduct.getProductId(), productDto.getProductId());
//        Assertions.assertEquals(existingProduct.getName(), productDto.getName());
//        Assertions.assertEquals(existingProduct.getDescription(), productDto.getDescription());
//        Assertions.assertEquals(existingProduct.getPrice(), productDto.getPrice());
//    }
//}