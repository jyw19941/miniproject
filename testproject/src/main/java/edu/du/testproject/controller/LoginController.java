package edu.du.testproject.controller;

import edu.du.testproject.entity.User;
import edu.du.testproject.spring.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("username")  // 세션에 username 저장
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 로그인 페이지 반환
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        // DB에서 사용자 정보 가져오기
        User user = userDAO.selectByEmail(email);

        // 사용자가 존재하지 않거나 비밀번호가 일치하지 않으면 오류 메시지 반환
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "login";  // 로그인 페이지로 다시 돌아가기
        }

        // 로그인 성공, 세션에 username 저장
        model.addAttribute("username", user.getUsername());
        return "redirect:/main";  // 로그인 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        // 세션에서 username을 null로 처리하여 세션 정보 삭제
        model.addAttribute("username", null);

        // 세션 무효화
        session.invalidate();

        // 세션 무효화 후에 메인 페이지로 리다이렉트
        return "redirect:/";  // 메인 페이지로 리다이렉트
    }
}
