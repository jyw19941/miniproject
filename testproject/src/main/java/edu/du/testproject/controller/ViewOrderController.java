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



        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            session.setAttribute("user", user);

            // 필요한 경우 user 객체를 DB에서 조회하여 정보 추가
        }
        session.getAttribute("user");
        System.out.println("==========================");
        List<Order> orders = orderRepository.findByUserId((int) user.getId());
        System.out.println("유저 ID받아오기 : " + user.getId());


        if(userDetails != null) {
            session.setAttribute("user", user);
            session.setAttribute("username", userDetails.getUsername());


            model.addAttribute("username", userDetails.getUsername());
        }


        model.addAttribute("orders", orders);

        return "view-order";
    }
}
