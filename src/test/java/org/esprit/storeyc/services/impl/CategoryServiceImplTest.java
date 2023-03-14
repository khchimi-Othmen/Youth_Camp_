package org.esprit.storeyc.services.impl;

import org.esprit.storeyc.dto.CategoryDto;
import org.esprit.storeyc.exception.EntityNotFoundException;
import org.esprit.storeyc.exception.ErrorCodes;
import org.esprit.storeyc.exception.InvalidEntityException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl service;

    @Test
    @Order(1)
    void shouldSaveCategoryWithSuccess() {
        CategoryDto expectedCategory = CategoryDto.builder()
                .name("Cat name")
                .description("description test")
                .code("Cat test")
                .build();
        CategoryDto savedCategory = service.createCategory(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getCategoryId());
        Assertions.assertEquals(expectedCategory.getName(), savedCategory.getName());
        Assertions.assertEquals(expectedCategory.getDescription(), savedCategory.getDescription());
        Assertions.assertEquals(expectedCategory.getCode(), savedCategory.getCode());
    }

    @Test
    @Order(2)
    void shouldupdateCategoryWithSuccess() {
        CategoryDto expectedCategory = CategoryDto.builder()
                .name("Cat name")
                .description("description test")
                .code("Cat test")
                .build();

        CategoryDto savedCategory = service.createCategory(expectedCategory);

        CategoryDto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = service.updateCategory(categoryToUpdate);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getCategoryId());
        Assertions.assertEquals(categoryToUpdate.getName(), savedCategory.getName());
        Assertions.assertEquals(categoryToUpdate.getDescription(), savedCategory.getDescription());
        Assertions.assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
    }

//    @Test
//    @Order(3)
//    void shouldThrowEntityNotFoundException() {
//        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));
//
//        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCodes());
//        assertEquals("Category with id = 0 not found in the DB", expectedException.getMessage());
//    }

    @Test
    @Order(4)
    void shouldThrowInvalidEntityException() {
        // Create an invalid category DTO
        CategoryDto invalidCategory = CategoryDto.builder()
                .name(null) // Name is required and is intentionally set to null to make the category invalid
                .description("Invalid category")
                .code("INV")
                .build();

        // Verify that creating an invalid category throws an InvalidEntityException
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> service.createCategory(invalidCategory));

        // Verify that the error codes and error message are correct
        assertEquals(ErrorCodes.INVALID_REQUEST, exception.getErrorCodes());
        assertEquals("The category is not valid", exception.getMessage());
    }

    @Test
    @Order(5)
    public void shouldThrowEntityNotFoundException(){
        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCodes());
        assertEquals("Category with id = 0 not found in the DB", expectedException.getMessage());
    }

    @Test
    @Order(6)
    void shouldDeleteCategoryWithSuccess() {
        // Create a category to be deleted
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Test Category")
                .description("A test category")
                .code("TEST")
                .build();
        CategoryDto savedCategory = service.createCategory(categoryDto);

        // Verify that the category was saved successfully
        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getCategoryId());

        // Delete the category and verify that it was deleted successfully
        service.deleteCategory(savedCategory.getCategoryId());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.findById(savedCategory.getCategoryId()));
        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, exception.getErrorCodes());
        assertEquals("Category with ID " + savedCategory.getCategoryId() + " not found", exception.getMessage());
    }

    @Test
    @Order(7)
    void shouldFindAllCategoriesWithSuccess() {
        // Create two categories
        CategoryDto categoryDto1 = CategoryDto.builder()
                .name("Category 1")
                .description("Description 1")
                .code("CODE1")
                .build();
        CategoryDto categoryDto2 = CategoryDto.builder()
                .name("Category 2")
                .description("Description 2")
                .code("CODE2")
                .build();
        service.createCategory(categoryDto1);
        service.createCategory(categoryDto2);

        // Find all categories and verify that both categories were returned
        List<CategoryDto> categoryDtoList = service.getAllCategories();
        assertEquals(2, categoryDtoList.size());
        assertTrue(categoryDtoList.stream().anyMatch(c -> c.getName().equals(categoryDto1.getName())));
        assertTrue(categoryDtoList.stream().anyMatch(c -> c.getName().equals(categoryDto2.getName())));
    }


}
