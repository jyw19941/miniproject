package edu.du.testproject.controller;

import edu.du.testproject.spring.UserDAO;
import edu.du.testproject.spring.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("user", new UserDTO()); // 빈 DTO 객체를 모델에 추가
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("user") UserDTO user, Model model) {
        if (userDAO.selectByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email is already in use.");
            return "signup";
        }
        userDAO.insert(user);
        return "redirect:/login";
    }
}
