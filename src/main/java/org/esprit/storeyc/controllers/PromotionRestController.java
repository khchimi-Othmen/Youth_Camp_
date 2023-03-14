package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Promotion Management")
@RestController
@RequestMapping("/Promotion")
public class PromotionRestController {

	@Autowired
	private IProductService productService;


	@GetMapping("/getProductsByPromotion")
	public List<ProductDto> getProductsByPromotion(@RequestParam("promotionName") String promotionName) {
		return productService.getProductsByPromotion(promotionName);
	}

	@PutMapping("/{productId}/addPromotionToProduct")
	public void addPromotionToProduct(@PathVariable("productId") Integer productId, @RequestParam("promotionName") String promotionName) {
		productService.addPromotionToProduct(productId, promotionName);
	}
	@PutMapping("/{productId}/{discount}/applyDiscountToProduct")
	public void applyDiscountToProduct(@PathVariable("productId") Integer productId, @PathVariable("discount") BigDecimal discount) {
		productService.applyDiscountToProduct(productId, discount);
	}

	@PutMapping("/{productId}/applyPercentageDiscountToProduct")
	public void applyPercentageDiscountToProduct(@PathVariable Integer productId, @RequestParam float percentageDiscount) {
		productService.applyPercentageDiscountToProduct(productId, percentageDiscount);
	}


	@DeleteMapping("/{productId}/removePromotionFromProduct")
	public void removePromotionFromProduct(@PathVariable("productId") Integer productId) {
		productService.removePromotionFromProduct(productId);
	}


}
