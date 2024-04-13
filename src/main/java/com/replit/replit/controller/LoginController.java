package com.replit.replit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/")
    public String loginPage(Model model) {
        return "startpage";
    }
}
