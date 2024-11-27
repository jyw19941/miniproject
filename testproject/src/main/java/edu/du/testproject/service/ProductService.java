package edu.du.testproject.service;

import edu.du.testproject.entity.Product;
import edu.du.testproject.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

   @Autowired
   private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 제품 추가
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 모든 제품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ID로 특정 제품 조회
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
        }
    }

//    // 제품 업데이트
//    public Product updateProduct(int id, Product updatedProduct) {
//        return productRepository.findById(id)
//                .map(product -> {
//                    product.setProductName(updatedProduct.getProductName());
//                    product.setProductPrice(updatedProduct.getProductPrice());
//                    product.setProductDescription(updatedProduct.getProductDescription());
//                    product.setProductImage(updatedProduct.getProductImage());
//                    return productRepository.save(product);
//                })
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//
//    // 제품 삭제
//    public void deleteProduct(int id) {
//        productRepository.deleteById(id);
//    }
