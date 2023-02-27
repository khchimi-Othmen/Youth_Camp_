package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "products Management")
@RestController
@RequestMapping("/products")
public class ProductRestController {

	@Autowired
	private IProductService productService;

	@PostMapping("/createProduct")
	public ProductDto createProduct(@RequestBody ProductDto productDto) {
		return productService.createProduct(productDto);
	}

	@PutMapping("/updateProduct")
	public void updateProduct(@RequestBody ProductDto updatedProductDto) {
		productService.updateProduct(updatedProductDto);
	}

	@DeleteMapping("/deleteProduct/{id}")
	public void deleteProduct(@PathVariable("id") Integer productId) {
		productService.deleteProduct(productId);
	}

	@GetMapping("/getProductById/{id}")
	public ProductDto getProductById(@PathVariable("id") Integer productId) {
		return productService.getProductById(productId);
	}

	@GetMapping("/getAllProducts")
	public List<ProductDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/searchProductsByName")
	public List<ProductDto> searchProductsByName(@RequestParam("name") String name) {
		return productService.searchProductsByName(name);
	}


//
//	@GetMapping("/getProductsByPromotion")
//	public List<ProductDto> getProductsByPromotion(@RequestParam("promotionName") String promotionName) {
//		return productService.getProductsByPromotion(promotionName);
//	}
//
//	@PutMapping("/{productId}/addPromotionToProduct")
//	public void addPromotionToProduct(@PathVariable("productId") Integer productId, @RequestParam("promotionName") String promotionName) {
//		productService.addPromotionToProduct(productId, promotionName);
//	}
//	@PutMapping("/{productId}/{discount}/applyDiscountToProduct")
//	public void applyDiscountToProduct(@PathVariable("productId") Integer productId, @PathVariable("discount") BigDecimal discount) {
//		productService.applyDiscountToProduct(productId, discount);
//	}
//
//	@PutMapping("/{productId}/applyPercentageDiscountToProduct")
//	public void applyPercentageDiscountToProduct(@PathVariable Integer productId, @RequestParam BigDecimal percentageDiscount) {
//		productService.applyPercentageDiscountToProduct(productId, percentageDiscount);
//	}
//
//
//	@DeleteMapping("/{productId}/removePromotionFromProduct")
//	public void removePromotionFromProduct(@PathVariable("productId") Integer productId) {
//		productService.removePromotionFromProduct(productId);
//	}
//
//	@PostMapping("/addRental")
//	public ProductDto addRental(@RequestBody ProductDto productDto) {
//		return productService.addRental(productDto);
//	}
//
//	@PostMapping("/addSale")
//	public ProductDto addSale(@RequestBody ProductDto productDto) {
//		return productService.addSale(productDto);
//	}
//
//	@GetMapping("/getAllRentals")
//	public List<ProductDto> getAllRentals() {
//		return productService.getAllRentals();
//	}
//
//	@GetMapping("/getAllSales")
//	public List<ProductDto> getAllSales() {
//		return productService.getAllSales();
//	}
//
//	@DeleteMapping("/deleteRental/{rentalId}")
//	public void deleteRental(@PathVariable Integer rentalId) {
//		productService.deleteRental(rentalId);
//	}
//
//	@DeleteMapping("/deleteSale/{saleId}")
//	public void deleteSale(@PathVariable Integer saleId) {
//		productService.deleteSale(saleId);
//	}
//
//	@PostMapping("/{productId}/processPayment")
//	public void processPayment(@PathVariable("productId") Integer productId, @RequestParam("amount") BigDecimal amount) {
//		productService.processPayment(productId, amount);
//	}
}
