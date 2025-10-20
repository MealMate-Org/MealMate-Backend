// package com.MealMate.MealMateBackend.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .cors().and()
//             .csrf().disable()
//             .authorizeHttpRequests(authz -> authz
//                 .requestMatchers("/api/public/**").permitAll() // Permite acceso público a ciertas rutas
//                 .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
//             )
//             .httpBasic(); // Usa autenticación básica (usuario y contraseña)

//         return http.build();
//     }
// }