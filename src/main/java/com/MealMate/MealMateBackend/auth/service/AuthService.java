package com.MealMate.MealMateBackend.auth.service;

import com.MealMate.MealMateBackend.auth.dto.AuthResponse;
import com.MealMate.MealMateBackend.auth.dto.LoginRequest;
import com.MealMate.MealMateBackend.user.dto.UserDTO;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

/**
 * ============================================
 * SERVICIO DE AUTENTICACIÓN
 * ============================================
 * 
 * Maneja la lógica de autenticación:
 * - Validar credenciales
 * - Generar tokens JWT
 */

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Autenticar usuario
     */
    public AuthResponse authenticate(LoginRequest loginRequest) {
        // Buscar usuario por email
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();

        // Verificar contraseña (SIMPLE - sin encriptación por ahora)
        // TODO: Implementar BCrypt cuando configures Spring Security
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token JWT simple (Base64 del userId por ahora)
        // TODO: Implementar JWT real con library como jjwt
        String token = generateSimpleToken(user.getId());

        // Mapear a DTO (no devolver la contraseña)
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // Crear respuesta
        return new AuthResponse(token, userDTO);
    }

    /**
     * Generar un token simple
     * NOTA: Esto es temporal. En producción debes usar JWT real
     */
    private String generateSimpleToken(Long userId) {
        String payload = "user:" + userId + ":timestamp:" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }
}