package edu.du.testproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

    // 메인 페이지
    @GetMapping("/")
    public String main(@SessionAttribute(value = "username", required = false) String username, Model model) {
        // 로그인된 상태인지 확인
        if (username != null) {
            model.addAttribute("username", username);  // 세션에서 username을 모델에 추가
        }
        return "main";  // 메인 페이지 반환
    }

    @GetMapping("/main")
    public String mainpage(@SessionAttribute(value = "username", required = false) String username, Model model) {
        if (username != null) {
            model.addAttribute("username", username);  // 세션에서 username을 모델에 추가
        }
        return "main";  // 메인 페이지 반환
    }

    // 로그인 페이지
    @GetMapping("/index")
    public String index(@SessionAttribute(value = "username", required = false) String username, Model model) {
        if (username != null) {
            model.addAttribute("username", username);  // 세션에서 username을 모델에 추가
        }
        return "index";
    }



    @GetMapping("/enrollment")
    public String enrollment(@SessionAttribute(value = "username", required = false) String username, Model model) {
        if (username != null) {
            model.addAttribute("username", username);  // 세션에서 username을 모델에 추가
        }
        return "enrollment";
    }
}
