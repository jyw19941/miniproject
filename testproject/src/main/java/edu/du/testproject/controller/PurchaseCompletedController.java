package edu.du.testproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseCompletedController {


    @GetMapping("/Purchase-success")
    public String purchaseSuccess(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails != null){
            model.addAttribute("username", userDetails.getUsername());
        }
        return "Purchase-success";
    }
}
