package com.ecomptaia.security.controller;

import com.ecomptaia.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> request) {
        Map<String, Object> response = authService.register(
                request.get("username"),
                request.get("email"),
                request.get("password"),
                request.get("firstName"),
                request.get("lastName")
        );
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = authService.login(
                request.get("username"),
                request.get("password")
        );
        return ResponseEntity.ok(response);
    }
}
