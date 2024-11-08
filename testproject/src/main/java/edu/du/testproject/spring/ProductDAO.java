package edu.du.testproject.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 상품 추가
    public void insertProduct(ProductDTO product) {
        String sql = "INSERT INTO products (product_name, product_price, product_description, product_image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getProductName(), product.getProductPrice(), product.getProductDescription(), product.getProductImage());
    }

    // 모든 상품 조회
    public List<ProductDTO> selectAllProducts() {
        String sql = "SELECT * FROM products";
        List<ProductDTO> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));

        // 데이터가 잘 조회되는지 로그 출력
        System.out.println("Retrieved products: " + products.size());
        for (ProductDTO product : products) {
            System.out.println("Product: " + product.getProductName() + ", Price: " + product.getProductPrice());
        }

        return products;
    }
}
