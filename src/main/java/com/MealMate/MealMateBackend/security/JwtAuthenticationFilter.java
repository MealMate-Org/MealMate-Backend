package com.MealMate.MealMateBackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ============================================
 * FILTRO DE AUTENTICACIÓN JWT
 * ============================================
 * 
 * Este filtro intercepta todas las peticiones y:
 * 1. Extrae el token JWT del header Authorization
 * 2. Valida el token
 * 3. Autentica al usuario en el contexto de seguridad
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        // 1. Extraer el header Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Verificar que el header existe y comienza con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitar "Bearer ")
        jwt = authHeader.substring(7);
        
        try {
            // 4. Extraer el email del token
            userEmail = jwtService.extractUsername(jwt);

            // 5. Si hay email y no hay autenticación previa
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // 6. Cargar los detalles del usuario
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // 7. Validar el token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    
                    // 8. Crear el token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    // 9. Agregar detalles de la petición
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    // 10. Establecer la autenticación en el contexto
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Si hay error al validar el token, simplemente continúa sin autenticar
            System.err.println("Error validando JWT: " + e.getMessage());
        }

        // 11. Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }
}
