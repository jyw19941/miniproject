package edu.du.testproject.controller;

import edu.du.testproject.entity.Product;
import edu.du.testproject.service.ProductService;
import edu.du.testproject.spring.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PaymentController {



    private final ProductService productService;

    public PaymentController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/payment/{id}")
    public String getPaymentPage(@PathVariable("id") int productId, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Product> product = productService.getProductById(productId);

        if (userDetails != null){
            model.addAttribute("username",userDetails.getUsername());
        }

        if (product.isEmpty()) {
            return "error";  //
        }

        model.addAttribute("product", product.get());
        return "payment";
    }

}
