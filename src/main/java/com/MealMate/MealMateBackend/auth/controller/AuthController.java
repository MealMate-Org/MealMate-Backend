package com.MealMate.MealMateBackend.auth.controller;

import com.MealMate.MealMateBackend.auth.dto.AuthResponse;
import com.MealMate.MealMateBackend.auth.dto.LoginRequest;
import com.MealMate.MealMateBackend.auth.dto.RegisterRequest;
import com.MealMate.MealMateBackend.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================
 * CONTROLADOR DE AUTENTICACIÓN
 * ============================================
 * 
 * Maneja el login y registro de usuarios y generación de tokens JWT
 */

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint de login
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de registro
     * POST /api/v1/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}