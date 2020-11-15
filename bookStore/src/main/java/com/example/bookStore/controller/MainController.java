package com.example.bookStore.controller;

import com.example.bookStore.entity.Users;
import com.example.bookStore.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal User user){
        HashMap<Object, Object> data = new HashMap<>();
        Users users = userRepo.findByUsername(user.getUsername());
        data.put("profile", users);
        model.addAttribute("frontendData", data);

        return "index";
    }

    @GetMapping("/registration")
    public String registrationView(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Users user, Model model){
        if (checkUser(user.getUsername())){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setId(UUID.randomUUID().toString());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            userRepo.save(user);
        }
        return "login";
    }

    private boolean checkUser(String username){
        Users user = userRepo.findByUsername(username);
        return user == null;
    }
}
