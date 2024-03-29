package org.esprit.storeyc.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.esprit.storeyc.dto.CategoryDto;
import org.esprit.storeyc.entities.Category;
import org.esprit.storeyc.entities.Product;
import org.esprit.storeyc.exception.EntityNotFoundException;
import org.esprit.storeyc.exception.ErrorCodes;
import org.esprit.storeyc.exception.InvalidEntityException;
import org.esprit.storeyc.repositories.CategoryRepository;
import org.esprit.storeyc.repositories.ProductRepository;
import org.esprit.storeyc.services.interfaces.ICategoryService;
import org.esprit.storeyc.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws InvalidEntityException {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if (!errors.isEmpty()) {
            log.error("Category is not valid: {}", errors);
            throw new InvalidEntityException("The category is not valid", ErrorCodes.INVALID_REQUEST, errors);
        }

        Category createdCategory = categoryRepository.save(CategoryDto.toEntity(categoryDto));
        return CategoryDto.fromEntity(createdCategory);
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if (!errors.isEmpty()) {
            log.error("Category is not valid: {}", errors);
            return null;
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getCategoryId());
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(categoryDto.getName());
            existingCategory.setDescription(categoryDto.getDescription());
            existingCategory.setCode(categoryDto.getCode());
            Category updatedCategory = categoryRepository.save(existingCategory);
            return CategoryDto.fromEntity(updatedCategory);
        } else {
            log.error("Category with ID {} not found", categoryDto.getCategoryId());
            return null;
        }
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if (categoryId == null) {
            log.error("Invalid category ID");
            return;
        }

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return CategoryDto.fromEntity(category);
        } else {
            log.error("Category with id {} not found", categoryId);
            return null;
        }
    }
    @Override
    public Category findCat(Integer id){
        categoryRepository.findById(id);
        return null;
    }


    public CategoryDto findById(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return null;
        }
        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category with id = " + id + " not found in the DB",
                        ErrorCodes.CATEGORY_NOT_FOUND));
    }


    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(CategoryDto.fromEntity(category));
        }
        return categoryDtos;
    }
    @Override
    public List<CategoryDto> getCategoriesByType(String categoryType) {
        List<Category> categories = categoryRepository.findByCategoryType(categoryType);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(CategoryDto.fromEntity(category));
        }
        return categoryDtos;
    }

    @Override
    public List<CategoryDto> searchCategoriesByName(String categoryName) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(categoryName);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(CategoryDto.fromEntity(category));
        }
        return categoryDtos;
    }
    @Override
    public void assignProductToCategory(Integer productId, Integer categoryId) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (product == null || category == null) {
            log.info("cant assignProductToCategory");
            return;
        }
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            log.info("Category not found");
            return null;
        }
        return productRepository.findByCategory(category);
    }

    public boolean isSustainable() {
        Category category =new Category();
        return category.getName().contains("*");
    }

//    @Override
//    public List<CategoryDto> filterCategoriesByTypeAndName(String categoryType, String categoryName) {
//        List<Category> categories = categoryRepository.findByCategoryDtoCategoryTypeAndNameContainingIgnoreCase(categoryType, categoryName);
//        List<CategoryDto> categoryDtos = new ArrayList<>();
//        for (Category category : categories) {
//            categoryDtos.add(CategoryDto.fromEntity(category));
//        }
//        return categoryDtos;
//    }


}
