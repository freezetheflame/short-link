package com.example.short_link.user.service;

import com.example.short_link.user.dao.User;
import com.example.short_link.user.dto.LoginRequest;
import com.example.short_link.user.dto.LoginResponse;
import com.example.short_link.user.dto.RegisterRequest;
import com.example.short_link.user.repo.UserRepository;
import com.example.short_link.utils.JwtUtils;
import com.example.short_link.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtils.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }


    public Boolean login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!PasswordUtils.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = JwtUtils.generateToken(user.getId(), user.getUsername());
        return true;
    }
}
