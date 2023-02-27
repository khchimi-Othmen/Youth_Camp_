package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Sale/Rental Management")
@RestController
@RequestMapping("/Sale/Rental")
public class SaleRentalRestController {

	@Autowired
	private IProductService productService;

	@PostMapping("/{productId}/addRental")
	public ProductDto addRental(@PathVariable("productId") Integer productId) {
		return productService.addRental(productId);
	}

	@PostMapping("/{productId}/addSale")
	public ProductDto addSale(@PathVariable("productId") Integer productId) {
		return productService.addSale(productId);
	}

	@GetMapping("/getAllRentals")
	public List<ProductDto> getAllRentals() {
		return productService.getAllRentals();
	}

	@GetMapping("/getAllSales")
	public List<ProductDto> getAllSales() {
		return productService.getAllSales();
	}

	@DeleteMapping("/deleteRental/{rentalId}")
	public void deleteRental(@PathVariable Integer rentalId) {
		productService.deleteRental(rentalId);
	}

	@DeleteMapping("/deleteSale/{saleId}")
	public void deleteSale(@PathVariable Integer saleId) {
		productService.deleteSale(saleId);
	}

	@PostMapping("/{productId}/processPayment")
	public void processPayment(@PathVariable("productId") Integer productId, @RequestParam("amount") BigDecimal amount) {
		productService.processPayment(productId, amount);
	}
}
