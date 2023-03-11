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


	@GetMapping("/getAllRentals")
	public List<ProductDto> getAllRentals() {
		return productService.getAllRentals();
	}

	@GetMapping("/getAllSales")
	public List<ProductDto> getAllSales() {
		return productService.getAllSales();
	}


}
