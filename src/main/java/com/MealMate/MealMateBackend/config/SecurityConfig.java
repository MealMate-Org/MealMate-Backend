// package com.MealMate.MealMateBackend.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             // IMPORTANTE: Habilitar CORS ANTES de cualquier otra configuración
//             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
//             // Deshabilitar CSRF (común en APIs REST)
//             .csrf(csrf -> csrf.disable())
            
//             // Configurar autorizaciones
//             .authorizeHttpRequests(auth -> auth
//                 // Permitir todas las peticiones (por ahora, para testing)
//                 // Más adelante puedes restringir rutas específicas
//                 .anyRequest().permitAll()
//             )
            
//             // Configuración de sesión (stateless para JWT)
//             .sessionManagement(session -> 
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//             );

//         return http.build();
//     }

//     /**
//      * Configuración de CORS
//      * Esta es la configuración que Spring Security usará
//      */
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
        
//         // Orígenes permitidos (tu frontend)
//         configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        
//         // O si quieres permitir cualquier origen (solo para desarrollo):
//         // configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
//         // Métodos HTTP permitidos
//         configuration.setAllowedMethods(Arrays.asList(
//             "GET", 
//             "POST", 
//             "PUT", 
//             "DELETE", 
//             "OPTIONS",
//             "PATCH"
//         ));
        
//         // Headers permitidos
//         configuration.setAllowedHeaders(Arrays.asList("*"));
        
//         // Permitir credenciales (cookies, authorization headers, etc.)
//         configuration.setAllowCredentials(true);
        
//         // Tiempo de caché para la respuesta preflight (en segundos)
//         configuration.setMaxAge(3600L);
        
//         // Headers expuestos (que el frontend puede leer)
//         configuration.setExposedHeaders(Arrays.asList(
//             "Authorization",
//             "Content-Type",
//             "X-Total-Count"
//         ));

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
//         // Aplicar esta configuración a todas las rutas
//         source.registerCorsConfiguration("/**", configuration);
        
//         return source;
//     }
// }