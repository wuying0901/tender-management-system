package com.zhaobiao.controller;

import com.zhaobiao.security.JwtTokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminAuthController {

    private final JwtTokenService jwtTokenService;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (adminUsername.equals(request.getUsername()) && adminPassword.equals(request.getPassword())) {
            String token = jwtTokenService.generateToken(adminUsername, 24L * 60 * 60 * 1000);
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(401).body("用户名或密码错误");
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class LoginResponse {
        private final String token;
    }
}


