package edu.du.testproject.controller;

import edu.du.testproject.entity.Product;
import edu.du.testproject.spring.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProductController {

    @Autowired
    ProductDAO productDAO;

//    @Autowired
//    private HttpServletRequest request;

//    @GetMapping("/product-enroll")
//    public String showProductEnrollPage() {
//        // 세션에서 로그인 여부 확인
//        if (request.getSession().getAttribute("username") == null) {
//            return "redirect:/login?error"; // 로그인되지 않으면 로그인 페이지로 리다이렉트
//        }
//        return "product-enroll"; // 로그인 된 경우 상품 등록 페이지로 이동
//    }

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

        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice((int)productPrice);
        product.setProductDescription(productDescription);
        product.setProductImage("/uploads/" + productImage.getOriginalFilename());

        productDAO.insertProduct(product);

        return "redirect:/sale";
    }

    @GetMapping("/sale")
    public String showSalePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("products", productDAO.selectAllProducts());
        if (userDetails != null){
            model.addAttribute("username",userDetails.getUsername());
        }
        return "sale";
    }
    @GetMapping("/product/{id}")
    public String getProductDetails(@PathVariable("id") int id, Model model) {
        Product product = productDAO.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }



}
