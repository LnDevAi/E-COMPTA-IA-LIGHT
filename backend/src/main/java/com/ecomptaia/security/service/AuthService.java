package com.ecomptaia.security.service;

import com.ecomptaia.security.entity.User;
import com.ecomptaia.security.repository.UserRepository;
import com.ecomptaia.security.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserLogService userLogService;
    
    public Map<String, Object> register(String username, String email, 
                                       String password, String firstName, String lastName) {
        
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(User.Role.USER);
        
    userRepository.save(user);
    userLogService.log(username, "register");
        
        String token = jwtService.generateToken(username, user.getRole().name());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        response.put("role", user.getRole());
        response.put("message", "Registration successful");
        
    return response;
    }
    
    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        if (!user.getActive()) {
            throw new RuntimeException("Account is inactive");
        }
        
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);
    userLogService.log(username, "login");
        
        String token = jwtService.generateToken(username, user.getRole().name());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        response.put("role", user.getRole());
        response.put("message", "Login successful");
        
    return response;
    }
}
