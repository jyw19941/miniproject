package edu.du.testproject.controller;

import edu.du.testproject.entity.User;
import edu.du.testproject.spring.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
        public String login(){
        return "login";
    }


    @GetMapping("/loginpage")
    public String loginForm() {
        return "login";  // 로그인 페이지로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userDAO.selectByEmail(email);
        System.out.println("유저 아이디:" + user.getId());
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("유저 아이디:" + user.getId());
            session.setAttribute("username", user.getUsername());  // 세션에 username 저장
            session.setAttribute("id", user.getId());
            session.setAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("id", user.getId());


            return "redirect:/main";  // 로그인 성공 후 /main 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // 로그인 실패 시 로그인 페이지로 돌아감
        }
    }




}
