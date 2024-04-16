package com.replit.replit.service;

import com.replit.replit.entity.User;

import java.security.Principal;

public interface UserService {
    User getCurrentUser(Principal principal);

    User findUserByName(String name);

    void save(User currentLoggedInUser);
}
