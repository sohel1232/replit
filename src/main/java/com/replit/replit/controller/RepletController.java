package com.replit.replit.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/")
public class RepletController {

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
                            Model model){
        System.out.println("Inside new Replet.Language chosen is : " + language);
        model.addAttribute("language",language);
        return "editor";
    }

}

