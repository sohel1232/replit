package com.replit.replit.service;

import com.replit.replit.entity.User;
import com.replit.replit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return userRepository.findByName(principal.getName());
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void save(User currentLoggedInUser) {
        userRepository.save(currentLoggedInUser);
    }
}
