package com.MealMate.MealMateBackend.config;

import com.MealMate.MealMateBackend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * ============================================
 * CONFIGURACIÓN DE SEGURIDAD
 * ============================================
 * 
 * Configura Spring Security con JWT:
 * - CORS
 * - Autenticación JWT
 * - Rutas públicas y protegidas
 * - BCrypt para encriptación de contraseñas
 * 
 * ✅ VERSIÓN ACTUALIZADA: Rutas públicas optimizadas
 * - GET /recipes es público (ver recetas sin login)
 * - POST/PUT/DELETE /recipes requiere autenticación
 * - /users está protegido excepto POST (registro)
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * ✅ IMPORTANTE: Configurar explícitamente el AuthenticationProvider
     * Esto asegura que BCrypt se use correctamente para validar contraseñas
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        System.out.println("✅ AuthenticationProvider configurado con BCrypt");
        return authProvider;
    }

    /**
     * Configuración principal de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Habilitar CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Deshabilitar CSRF (no necesario para APIs REST con JWT)
            .csrf(csrf -> csrf.disable())
            
            // Configurar autorizaciones
            .authorizeHttpRequests(auth -> auth
                // === RUTAS PÚBLICAS (sin autenticación) ===
                
                // Autenticación
                .requestMatchers("/api/v1/auth/**").permitAll()
                
                // Registro de usuarios (POST)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/users").permitAll()
                
                // Ver recetas públicas (GET)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/recipes/**").permitAll()
                
                // Ver tipos de comida (GET)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/meal-types").permitAll()
                
                // Ver alérgenos (GET)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/allergens").permitAll()
                
                // Ver dietas (GET)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/diets").permitAll()
                
                // Información nutricional pública (GET)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/nutrition-info/**").permitAll()
                
                // Manejo de errores
                .requestMatchers("/error").permitAll()
                
                // === TODAS LAS DEMÁS RUTAS REQUIEREN AUTENTICACIÓN ===
                .anyRequest().authenticated()
            )
            
            // Configuración de sesión (stateless para JWT)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // ✅ Usar el AuthenticationProvider configurado
            .authenticationProvider(authenticationProvider())
            
            // Agregar el filtro JWT antes del filtro de autenticación
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configuración de CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", 
            "POST", 
            "PUT", 
            "DELETE", 
            "OPTIONS",
            "PATCH"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciales
        configuration.setAllowCredentials(true);
        
        // Tiempo de caché para preflight
        configuration.setMaxAge(3600L);
        
        // Headers expuestos
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Total-Count"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    /**
     * Gestor de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Encriptador de contraseñas (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}