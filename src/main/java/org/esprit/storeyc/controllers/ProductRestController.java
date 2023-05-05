package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Tag(name = "products Management")
@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductRestController {

	@Autowired
	private IProductService productService;

	@PostMapping("/createProductAndAssignToCategory/{categoryId}")
	public ProductDto createProductAndAssignToCategory(@RequestBody ProductDto productDto,
													   @PathVariable Integer categoryId
			/* @RequestParam(required = false, defaultValue = "false") boolean isRental*/) {
		return productService.createProductAndAssignToCategory(productDto,categoryId);
	}
	@PostMapping("/createProduct")
	public void createProduct(@RequestBody Product productDto) {
		productService.createProduct(productDto);
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

	@GetMapping("/getRandomProduct")
	public ProductDto getRandomProduct() {
		List<ProductDto> products = productService.getAllProducts();
		int index = new Random().nextInt(products.size());
		return products.get(index);
	}
	@GetMapping("/randomProducts")
	public ResponseEntity<List<ProductDto>> getRandomProducts() {
		List<ProductDto> randomProducts = productService.getRandomProducts(4);
		return new ResponseEntity<>(randomProducts, HttpStatus.OK);
	}


}
