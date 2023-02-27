package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.dto.CategoryDto;
import org.esprit.storeyc.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category Management")
@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@PostMapping("/createCategory")
	public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
		return categoryService.createCategory(categoryDto);
	}

	@PutMapping("/updateCategory")
	public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
		return categoryService.updateCategory(categoryDto);
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	public void deleteCategory(@PathVariable Integer categoryId) {
		categoryService.deleteCategory(categoryId);
	}

	@GetMapping("/getCategoryById/{categoryId}")
	public CategoryDto getCategoryById(@PathVariable Integer categoryId) {
		return categoryService.getCategoryById(categoryId);
	}

	@GetMapping("/getAllCategories")
	public List<CategoryDto> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping("/type/{categoryType}")
	public List<CategoryDto> getCategoriesByType(@PathVariable("categoryType") String categoryType) {
		return categoryService.getCategoriesByType(categoryType);
	}

	@GetMapping("/search")
	public List<CategoryDto> searchCategoriesByName(@RequestParam String categoryName) {
		return categoryService.searchCategoriesByName(categoryName);
	}

	@PostMapping("/{productId}/{categoryId}/assignProductToCategory/")
	public void assignProductToCategory(@PathVariable("productId") Integer productId,@PathVariable("categoryId") Integer categoryId) {
		categoryService.assignProductToCategory(productId,categoryId);
	}

}
