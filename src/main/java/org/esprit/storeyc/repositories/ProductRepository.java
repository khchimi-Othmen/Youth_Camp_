package org.esprit.storeyc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.esprit.storeyc.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


//    @Query("SELECT p FROM Product p WHERE p.category = :category")
//    List<Product> findByCategoryJPQL(@Param("category") CaType category);
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByAvailableTrue();

    List<Product> findByPromotion(String promotionName);
    @Query("SELECT p FROM Product p WHERE p.isRental = true")
    List<Product> findRentals();

    @Query("SELECT p FROM Product p WHERE p.isRental = false")
    List<Product> findSales();
}