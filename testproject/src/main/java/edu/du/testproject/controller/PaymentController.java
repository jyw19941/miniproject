package edu.du.testproject.controller;

import edu.du.testproject.entity.Product;
import edu.du.testproject.entity.User;
import edu.du.testproject.service.ProductService;
import edu.du.testproject.spring.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
public class PaymentController {


    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);


    private final ProductService productService;

    public PaymentController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/payment/{id}")
    public String getPaymentPage(@PathVariable("id") int productId, Model model, @AuthenticationPrincipal UserDetails userDetails , User user, HttpSession session) {
        Optional<Product> product = productService.getProductById(productId);

        if (userDetails != null){
            model.addAttribute("username",userDetails.getUsername());
            session.setAttribute("user", user);

        }


        if (product.isEmpty()) {
            return "error";  //
        }

        model.addAttribute("product", product.get());
        return "payment";
    }

}
