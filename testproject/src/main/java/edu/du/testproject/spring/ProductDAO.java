package edu.du.testproject.spring;

import edu.du.testproject.entity.Product;
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
    public void insertProduct(Product product) {
        String sql = "INSERT INTO product(product_name, product_price, product_description, product_image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getProductName(), product.getProductPrice(), product.getProductDescription(), product.getProductImage());
    }

    // 모든 상품 조회
    public List<Product> selectAllProducts() {
        String sql = "SELECT * FROM product";
        List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));

        // 데이터가 잘 조회되는지 로그 출력
        System.out.println("Retrieved products: " + products.size());
        for (Product product : products) {
            System.out.println("Product: " + product.getProductName() + ", Price: " + product.getProductPrice());
        }

        return products;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
    }

    public Product getProductByName(String name) {
        String sql = "SELECT product_name FROM product WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), name);

        for (Product product : products) {
            System.out.println("Product: " + product.getProductName() + ", Price: " + product.getProductPrice());

        }
        return products.get(0);

    }

}
