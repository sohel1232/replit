package com.replit.replit.controller;

import com.replit.replit.Entity.Role;
import com.replit.replit.Entity.User;
import com.replit.replit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String loginPage(Model model) {
        return "startpage";
    }
    @GetMapping("/customLogin")
    public String customLoginPage(Model model)
    {
        return "login";
    }

    @GetMapping("/registerUser")
    public String registerUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model)
    {
        System.out.println(email);
        System.out.println(password);
        User user = new User();
        user.setName(email);
        user.setPassword(password);
        Role  role = new Role();
        role.setName("ROLE_AUTHOR");
        Collection<Role> collectionOfRoles = List.of(role);
        user.setRoles(collectionOfRoles);
        userService.save(user);
        return "login";
    }
}
