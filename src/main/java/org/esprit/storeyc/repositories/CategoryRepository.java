package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
        List<Category> findByCategoryType(String categoryType);

        List<Category> findByNameContainingIgnoreCase(String categoryName);

//        List<Category> findByCategoryDtoCategoryTypeAndNameContainingIgnoreCase(String categoryType, String name);
    }


