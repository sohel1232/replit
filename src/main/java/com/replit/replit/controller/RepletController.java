package com.replit.replit.controller;

import com.replit.replit.Entity.User;
import com.replit.replit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/")
public class RepletController {
    @Autowired
    UserService userService;


    @Controller
    public class HomeController {

        @GetMapping("/home")
        public String home(@AuthenticationPrincipal UserDetails userDetails, Authentication authentication, Model model) {
            if (authentication != null && authentication.isAuthenticated()) {
                if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
                    // User is authenticated via OAuth2 (Google)
                    OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
                    String email = oauth2Principal.getAttribute("email");
                    String name = oauth2Principal.getAttribute("name");
                    User user = userService.getUserByEmail(email);
                    if (user == null) {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setName(name);
                        userService.save(newUser);
                    }

                    // Use email and name as necessary
                    model.addAttribute("email", email);
                    model.addAttribute("name", name);
                } else {
                    // User is authenticated via custom login
                    if (userDetails != null) {
                        // Use userDetails.getUsername(), userDetails.getPassword(), etc. as necessary
                        System.out.println(userDetails.getUsername());
                        System.out.println(userDetails.getPassword());
                        model.addAttribute("username", userDetails.getUsername());
                    }
                }
            }
            return "home";
        }
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

