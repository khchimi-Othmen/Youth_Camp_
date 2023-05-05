package org.esprit.storeyc.repositories;

import org.esprit.storeyc.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.esprit.storeyc.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {



    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPromotion(String promotionName);
    @Query("SELECT p FROM Product p WHERE p.isRental = true")
    List<Product> findRentals();

    @Query("SELECT p FROM Product p WHERE p.isRental = false")
    List<Product> findSales();

//    @Query("SELECT p FROM Product p ORDER BY p.numLikes DESC, p.numDislikes ASC")
//    List<Object[]> findProductsByLikesAndDislikes();
//
List<Product> findByCategory(Category category);

}
