package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CategoryDto;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.Product;

import java.util.List;

public interface ICategoryService {
     CategoryDto createCategory(CategoryDto categoryDto) ;
     CategoryDto updateCategory(CategoryDto categoryDto) ;
     void deleteCategory(Integer categoryId) ;
     CategoryDto getCategoryById(Integer categoryId) ;
     List<CategoryDto> getAllCategories() ;
     List<CategoryDto> getCategoriesByType(String categoryType) ;
     List<CategoryDto> searchCategoriesByName(String categoryName) ;
     void assignProductToCategory(Integer productId, Integer categoryId) ;
     List<Product> getProductsByCategory(Integer categoryId) ;
     Category findCat(Integer id);

}
