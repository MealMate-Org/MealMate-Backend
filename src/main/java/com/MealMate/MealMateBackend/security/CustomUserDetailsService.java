package com.MealMate.MealMateBackend.security;

import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * ============================================
 * SERVICIO DE DETALLES DE USUARIO
 * ============================================
 * 
 * Implementa UserDetailsService de Spring Security
 * Carga usuarios desde la base de datos para autenticación
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    /**
     * Obtiene los roles/autoridades del usuario
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Por ahora, solo usamos el rol del usuario
        // Formato: ROLE_ADMIN, ROLE_USER, etc.
        String roleName = "ROLE_" + user.getRole().getName();
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }
}
