package com.MealMate.MealMateBackend.auth.service;

import com.MealMate.MealMateBackend.auth.dto.AuthResponse;
import com.MealMate.MealMateBackend.auth.dto.LoginRequest;
import com.MealMate.MealMateBackend.auth.dto.RegisterRequest;
import com.MealMate.MealMateBackend.security.JwtService;
import com.MealMate.MealMateBackend.user.dto.UserDTO;
import com.MealMate.MealMateBackend.user.model.Role;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
 * - Registro de nuevos usuarios
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
        System.out.println("🔐 ===== INICIO DE AUTENTICACIÓN =====");
        System.out.println("📧 Email: " + loginRequest.getEmail());
        
        try {
            // 1. Verificar que el usuario existe ANTES de autenticar
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> {
                        System.err.println("❌ Usuario no encontrado: " + loginRequest.getEmail());
                        return new BadCredentialsException("Usuario no encontrado");
                    });
            
            System.out.println("✅ Usuario encontrado: " + user.getUsername());
            System.out.println("🔑 Hash en DB: " + user.getPassword().substring(0, 20) + "...");
            
            // 2. Verificar manualmente la contraseña (para debugging)
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            System.out.println("🔍 Password matches: " + passwordMatches);
            
            if (!passwordMatches) {
                System.err.println("❌ Contraseña incorrecta para: " + loginRequest.getEmail());
                throw new BadCredentialsException("Contraseña incorrecta");
            }
            
            // 3. Autenticar con Spring Security
            System.out.println("🔄 Intentando autenticar con AuthenticationManager...");
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            System.out.println("✅ Autenticación exitosa con AuthenticationManager");

            // 4. Cargar UserDetails para generar el token
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            System.out.println("✅ UserDetails cargado: " + userDetails.getUsername());

            // 5. Generar token JWT real
            String token = jwtService.generateToken(userDetails);
            System.out.println("🎫 Token JWT generado: " + token.substring(0, 50) + "...");

            // 6. Mapear a DTO (no devolver la contraseña)
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            System.out.println("✅ UserDTO creado para: " + userDTO.getUsername());

            // 7. Crear respuesta
            System.out.println("✅ ===== AUTENTICACIÓN COMPLETADA =====");
            return new AuthResponse(token, userDTO);
            
        } catch (BadCredentialsException e) {
            System.err.println("❌ ===== ERROR: CREDENCIALES INVÁLIDAS =====");
            System.err.println("Mensaje: " + e.getMessage());
            throw e;
        } catch (AuthenticationException e) {
            System.err.println("❌ ===== ERROR DE AUTENTICACIÓN =====");
            System.err.println("Tipo: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            throw new BadCredentialsException("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ ===== ERROR INESPERADO =====");
            System.err.println("Tipo: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado durante la autenticación: " + e.getMessage());
        }
    }

    /**
     * Registro de nuevo usuario con autenticación automática
     */
    public AuthResponse register(RegisterRequest registerRequest) {
        System.out.println("📝 ===== INICIO DE REGISTRO =====");
        System.out.println("📧 Email: " + registerRequest.getEmail());
        System.out.println("👤 Username: " + registerRequest.getUsername());
        
        try {
            // 1. Verificar si el email ya existe
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                System.err.println("❌ Email ya existe: " + registerRequest.getEmail());
                throw new RuntimeException("El email ya está registrado");
            }
            
            // 2. Crear nuevo usuario
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setAvatar(registerRequest.getAvatar());
            user.setBio(registerRequest.getBio());
            
            // 3. Asignar rol (por defecto USER = 2)
            Role role = new Role();
            role.setId(registerRequest.getRoleId() != null ? registerRequest.getRoleId() : 2);
            user.setRole(role);
            
            // 4. Encriptar contraseña ANTES de guardar
            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            System.out.println("🔐 Contraseña encriptada: " + encodedPassword.substring(0, 20) + "...");
            user.setPassword(encodedPassword);
            
            // 5. Guardar usuario
            User savedUser = userRepository.save(user);
            System.out.println("✅ Usuario guardado con ID: " + savedUser.getId());
            
            // 6. Generar token JWT para login automático
            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
            String token = jwtService.generateToken(userDetails);
            System.out.println("🎫 Token JWT generado para nuevo usuario");
            
            // 7. Mapear a DTO (no devolver la contraseña)
            UserDTO userDTO = modelMapper.map(savedUser, UserDTO.class);
            
            System.out.println("✅ ===== REGISTRO COMPLETADO =====");
            return new AuthResponse(token, userDTO);
            
        } catch (Exception e) {
            System.err.println("❌ ===== ERROR EN REGISTRO =====");
            System.err.println("Tipo: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage());
        }
    }
}