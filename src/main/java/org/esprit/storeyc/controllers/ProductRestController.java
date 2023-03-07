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




	}
