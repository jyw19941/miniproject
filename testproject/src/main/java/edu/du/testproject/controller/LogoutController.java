package edu.du.testproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller

public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {


        session.removeAttribute("username");  // 세션에서 username 삭제
        session.removeAttribute("id");        // 세션에서 id 삭제
        session.removeAttribute("user");// 세션 무효화
        session.invalidate();

        return "redirect:/";  // 홈 페이지로 리다이렉트
    }
}
