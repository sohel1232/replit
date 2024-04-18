package com.replit.replit.controller;

import com.replit.replit.entity.Role;
import com.replit.replit.entity.User;
import com.replit.replit.service.RoleService;
import com.replit.replit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {

    UserService userService;
    RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

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
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("user-name") String userName,
                               Model model)
    {    System.out.println(userName);
        System.out.println(email);
        System.out.println(password);
        User user = new User();
        user.setName(userName);
        user.setEmail(email);
        user.setPassword(password);
        Role role = new Role();
        role.setName("ROLE_AUTHOR");
        roleService.saveRole(role);
        Collection<Role> collectionOfRoles = List.of(role);
        user.setRoles(collectionOfRoles);
        System.out.println("going to save user");

        userService.save(user);
        System.out.println("User saved. user = " + user);
        return "login";
    }
    @GetMapping("/signUp")
    public String signUp()
    {
        return "signup";
    }

    @GetMapping("/createUser")
    public String createUser()
    {
        return "createnew";
    }
}
