package edu.du.testproject.controller;


import edu.du.testproject.entity.Order;
import edu.du.testproject.entity.User;
import edu.du.testproject.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ViewOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/view-order")
    public String showviewOrder(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session) {

        // 세션에서 userId를 가져옵니다.
        Long userId = (Long) session.getAttribute("userId");


        if (userId == null) {
            // 만약 userId가 세션에 없다면 로그인된 사용자가 없거나 세션이 만료된 경우
            System.out.println("유저 아이디없음");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트합니다.
        }

        // userId를 기준으로 User 객체를 DB에서 조회합니다.
        User user = (User) orderRepository.findByUserId(userId);

        if (user == null) {
            // userId가 유효하지 않으면 새 User 객체를 생성하여 세션에 저장하고, view-order 페이지로 리다이렉트합니다.
            user = new User();
            session.setAttribute("userId", user.getId());
            return "redirect:/view-order";
        }

        System.out.println("==========================");

        // 주문 목록을 userId를 기준으로 조회합니다.
        List<Order> orders = orderRepository.findByUser(userId);
        if (orders != null && !orders.isEmpty()) {
            Order order = orders.get(0);
        }
        System.out.println("유저 ID받아오기 : " + user.getId());

        if (userDetails != null) {
            // 로그인된 사용자 정보와 username을 세션에 저장합니다.
            session.setAttribute("user", user);
            session.setAttribute("username", userDetails.getUsername());
        }

        // 모델에 사용자 이름과 주문 목록을 추가합니다.
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("orders", orders);

        return "view-order";
    }
}
