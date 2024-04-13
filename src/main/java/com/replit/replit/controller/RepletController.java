package com.replit.replit.controller;

import com.replit.replit.Entity.User;
import com.replit.replit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/")
public class RepletController {
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(OAuth2AuthenticationToken authentication, Model model){
        OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        String name = oauth2Principal.getAttribute("name");
        User user = userService.getUserByEmail(email);
        if(user == null){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            userService.save(newUser);
        }
        return "home";
    }

    @GetMapping("/new-replet")
    public String newRepletForm(){
        return "replet-form";
    }

    @PostMapping("/new-replet")
    public String newReplet(@RequestParam String language,
                            Model model){
        System.out.println("Inside new Replet.Language chosen is : " + language);
        model.addAttribute("language",language);
        return "editor";
    }

}

