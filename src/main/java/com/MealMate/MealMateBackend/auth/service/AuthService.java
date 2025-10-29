package com.MealMate.MealMateBackend.auth.service;

import com.MealMate.MealMateBackend.auth.dto.AuthResponse;
import com.MealMate.MealMateBackend.auth.dto.LoginRequest;
import com.MealMate.MealMateBackend.security.JwtService;
import com.MealMate.MealMateBackend.user.dto.UserDTO;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ============================================
 * SERVICIO DE AUTENTICACIÓN
 * ============================================
 * 
 * Maneja la lógica de autenticación:
 * - Validar credenciales con BCrypt
 * - Generar tokens JWT reales
 * - Manejo de errores de autenticación
 */

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Autenticar usuario con Spring Security y JWT
     */
    public AuthResponse authenticate(LoginRequest loginRequest) {
        System.out.println("🔐 Intentando autenticar usuario: " + loginRequest.getEmail());
        
        try {
            // 1. Autenticar con Spring Security
            // Esto validará automáticamente usuario y contraseña
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            System.out.println("✅ Autenticación exitosa");

            // 2. Buscar usuario en la base de datos
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // 3. Cargar UserDetails para generar el token
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // 4. Generar token JWT real
            String token = jwtService.generateToken(userDetails);
            System.out.println("🎫 Token JWT generado correctamente");

            // 5. Mapear a DTO (no devolver la contraseña)
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            // 6. Crear respuesta
            return new AuthResponse(token, userDTO);
            
        } catch (Exception e) {
            System.err.println("❌ Error de autenticación: " + e.getMessage());
            throw new RuntimeException("Credenciales inválidas: " + e.getMessage());
        }
    }

    /**
     * Registro de nuevo usuario (opcional - para futuro)
     */
    public AuthResponse register(User user) {
        // Encriptar contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Guardar usuario
        User savedUser = userRepository.save(user);
        
        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtService.generateToken(userDetails);
        
        // Mapear a DTO
        UserDTO userDTO = modelMapper.map(savedUser, UserDTO.class);
        
        return new AuthResponse(token, userDTO);
    }
}
