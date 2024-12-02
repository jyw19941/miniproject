package edu.du.testproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    // 메인 페이지
    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인된 상태인지 확인
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());  // 세션에서 username을 모델에 추가

        }
        return "main";  // 메인 페이지 반환
    }

    @GetMapping("/main")
    public String mainpage(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session) {
         User user = (User) session.getAttribute("id");

        edu.du.testproject.entity.User user1 = new edu.du.testproject.entity.User();
        session.setAttribute("userId",user1.getId());
         session.setAttribute("user", user);
//        public String mainpage(@SessionAttribute(value = "username", required = false) String username, Model model) {
        if (userDetails != null) {
            session.setAttribute("username", userDetails.getUsername());
            session.setAttribute("user",user);
            session.setAttribute("userId",user1.getId());

            model.addAttribute("username", userDetails.getUsername());  // 세션에서 username을 모델에 추가
        }else {
            System.out.println("세션 null");
        }
        return "main";  // 메인 페이지 반환
    }





    @GetMapping("/enrollment")
    public String enrollment(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());  // 세션에서 username을 모델에 추가
        }
        return "enrollment";
    }
}
