package edu.du.testproject.controller;

import edu.du.testproject.entity.Order;
import edu.du.testproject.entity.Product;
import edu.du.testproject.entity.User;
import edu.du.testproject.service.OrderService;
import edu.du.testproject.service.ProductService;
import edu.du.testproject.service.UserService;
import edu.du.testproject.spring.ProductNotFoundException;
import edu.du.testproject.spring.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;



@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    // 결제 처리 페이지 요청


    // 결제 처리
    @PostMapping("/process-payment")
    public String processPayment(
                                 @RequestParam(required = false) int productId,
                                 @RequestParam String size,
                                 @RequestParam String paymentMethod,
                                 Model model, HttpSession session) {

            User user = (User) session.getAttribute("user");
            System.out.println("==========");
            System.out.println("유저 ID받아오기" + user.getId());





        // 상품 정보 가져오기
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // 주문 생성 (totalPrice를 계산하여 저장)
        Order order = orderService.createOrder(user, product, size, paymentMethod);

        // 결제 상태 및 트랜잭션 설정 (예시)
        order.setPaymentStatus("Completed");
        order.setTransactionId("TXN12345");
        order.setPaymentDate(LocalDateTime.now());

        // 주문 저장
        orderService.saveOrder(order);

        model.addAttribute("order", order);
        return "redirect:Purchase-success"; // 주문 요약 페이지로 이동
    }
}
