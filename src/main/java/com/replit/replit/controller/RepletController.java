package com.replit.replit.controller;

import com.replit.replit.entity.Replit;
import com.replit.replit.entity.User;
import com.replit.replit.service.ReplitService;
import com.replit.replit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/replit")
public class RepletController {

    UserService userService;
    ReplitService replitService;

    @Autowired
    public RepletController(UserService userService, ReplitService replitService) {
        this.userService = userService;
        this.replitService = replitService;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/new-replet")
    public String newRepletForm(){
        return "replet-form";
    }

    @PostMapping("/new-replet")
    public String newReplet(@RequestParam String language,
                            @RequestParam String name,
                            Model model,
                            Principal principal){
        //User currentLoggedInUser = userService.getCurrentUser(principal);
        User currentLoggedInUser = userService.getCurrentUser(principal);

        Replit replit = new Replit(name,language);
        currentLoggedInUser.getReplits().add(replit);
        replit.setUser(currentLoggedInUser);

        userService.save(currentLoggedInUser);
        replitService.save(replit);

       // currentLoggedInUser.getReplits().add(replit);

       // Replit myReplit = replitService.findByName("yash code");

        System.out.println("Inside new-replet. replet created is : " + replit);
        model.addAttribute("replit",replit);
        return "editor";
    }

    @PostMapping("/update-replit")
    public String updateReplit(@RequestParam Integer replitId , Model model){
        Replit replit = replitService.findById(replitId);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        replit.setUpdatedAt(localDateTime);
        System.out.println("Inside update replit, replis is : " + replit);
        model.addAttribute("replit" , replit);
        return "editor";
    }

    @GetMapping("/myrepl")
    public String showRepl(){
        return "display";
    }
}

