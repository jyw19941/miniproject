package edu.du.testproject.controller;

import edu.du.testproject.spring.ProductDAO;
import edu.du.testproject.spring.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @PostMapping("/submit-product")
    public String submitProduct(@RequestParam("productName") String productName,
                                @RequestParam("productPrice") double productPrice,
                                @RequestParam("productDescription") String productDescription,
                                @RequestParam("productImage") MultipartFile productImage) throws IOException {

        String uploadsDir = "C:/uploads/";
        Path uploadPath = Paths.get(uploadsDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String imagePath = uploadsDir + productImage.getOriginalFilename();
        productImage.transferTo(new File(imagePath));

        ProductDTO product = new ProductDTO();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductDescription(productDescription);
        product.setProductImage("/uploads/" + productImage.getOriginalFilename());

        productDAO.insertProduct(product);

        return "redirect:/sale";
    }

    @GetMapping("/sale")
    public String showSalePage(Model model) {
        List<ProductDTO> products = productDAO.selectAllProducts();

        // products가 비어있는지 확인하기 위해 로그 출력
        System.out.println("Products size: " + products.size());

        model.addAttribute("products", products);  // 상품 목록 추가
        return "sale";  // "sale" 뷰 반환
    }
}
