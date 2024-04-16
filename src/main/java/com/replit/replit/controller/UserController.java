package com.replit.replit.controller;

import com.replit.replit.entity.Replit;
import com.replit.replit.entity.User;
import com.replit.replit.service.ReplitService;
import com.replit.replit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    ReplitService replitService;

    @Autowired
    public UserController(UserService userService, ReplitService replitService) {
        this.userService = userService;
        this.replitService = replitService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        User currentLoggedInUser = userService.findUserByName("sohel");
        System.out.println("Logged in user : " + currentLoggedInUser);
        List<Replit> replits = replitService.findReplitsByUser(currentLoggedInUser);
        System.out.println("Sohel replits: " + replits);

        Replit replit = new Replit("abc");
        Replit replit2 = new Replit("cde");

        List<Replit> replitList = new ArrayList<>();



        model.addAttribute("replits" , replits);
        return "profile";
    }
}
