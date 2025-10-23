package com.MealMate.MealMateBackend.auth.dto;

import com.MealMate.MealMateBackend.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de autenticación
 * Incluye el token JWT y los datos del usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDTO user;
}