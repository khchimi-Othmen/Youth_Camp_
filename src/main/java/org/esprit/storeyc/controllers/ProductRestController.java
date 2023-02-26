package org.esprit.storeyc.controllers;

import org.esprit.storeyc.dto.ProductDto;
import org.esprit.storeyc.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private IProductService productService;

//	@PostMapping("/createProduct")
//	public ProductDto createProduct(@RequestBody ProductDto productDto) {
//		return productService.createProduct(productDto);
//	}
		@PostMapping("/createProduct")
		public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		ProductDto createdProduct = productService.createProduct(productDto);
		if (createdProduct != null) {
			return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateProduct")
	public ResponseEntity<Void> updateProduct(@RequestBody ProductDto updatedProductDto) {
		productService.updateProduct(updatedProductDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getProductById/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Integer productId) {
		ProductDto productDto = productService.getProductById(productId);
		if (productDto != null) {
			return new ResponseEntity<>(productDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

//	@GetMapping("/filterProductsByCategory/{category}")
//	public ResponseEntity<List<ProductDto>> filterProductsByCategory(@PathVariable("category") CaType category) {
//		List<ProductDto> products = productService.filterProductsByCategory(category);
//		return new ResponseEntity<>(products, HttpStatus.OK);
//	}

	@GetMapping("/searchProductsByName")
	public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam("name") String name) {
		List<ProductDto> products = productService.searchProductsByName(name);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
