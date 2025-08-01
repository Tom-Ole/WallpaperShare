package com.taskmanager.taskmanager.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskmanager.taskmanager.entity.UserEntity;
import com.taskmanager.taskmanager.repository.UserRepository;

@Controller
public class ProfileController {

    UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/p/{username}")
    public String profile(Model model, @PathVariable String username) {

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            System.out.println("User not found: " + username);
            return "redirect:/error"; // Redirect to an error page if user not found
        }

        System.out.println("User found: " + user.get().getUsername());

        model.addAttribute("user", user.get());

        return "profile";
    }
}
