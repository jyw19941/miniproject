package edu.du.testproject.respository;

import edu.du.testproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Integer> {

    List<Product> findByProductPriceBetween(Double minPrice, Double maxPrice);
}
