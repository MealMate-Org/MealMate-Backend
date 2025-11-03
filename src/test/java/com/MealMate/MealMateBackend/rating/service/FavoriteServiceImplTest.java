package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.model.Favorite;
import com.MealMate.MealMateBackend.rating.model.FavoriteKey;
import com.MealMate.MealMateBackend.rating.repository.FavoriteRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private Favorite favorite;
    private FavoriteDTO favoriteDTO;
    private User user;
    private FavoriteKey favoriteKey;

    @BeforeEach
    void setUp() {
        favoriteKey = new FavoriteKey(1L, 1L);
        
        favorite = new Favorite();
        favorite.setId(favoriteKey);
        favorite.setCreatedAt(LocalDateTime.now());

        favoriteDTO = new FavoriteDTO();
        favoriteDTO.setUserId(1L);
        favoriteDTO.setRecipeId(1L);
        favoriteDTO.setCreatedAt(LocalDateTime.now());

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getAllFavorites_ShouldReturnUserFavorites() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(favorite));

        // Act
        List<FavoriteDTO> result = favoriteService.getAllFavorites();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        assertThat(result.get(0).getRecipeId()).isEqualTo(1L);
        verify(favoriteRepository, times(1)).findAll();
    }

    @Test
    void getAllFavorites_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> favoriteService.getAllFavorites())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    void getFavoriteById_ShouldReturnFavorite_WhenExists() {
        // Arrange
        when(favoriteRepository.findByUserIdAndRecipeId(1L, 1L)).thenReturn(Optional.of(favorite));

        // Act
        FavoriteDTO result = favoriteService.getFavoriteById(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRecipeId()).isEqualTo(1L);
        verify(favoriteRepository, times(1)).findByUserIdAndRecipeId(1L, 1L);
    }

    @Test
    void getFavoriteById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(favoriteRepository.findByUserIdAndRecipeId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> favoriteService.getFavoriteById(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Favorite not found");
    }

    @Test
    void createFavorite_ShouldCreateFavorite() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);

        // Act
        FavoriteDTO result = favoriteService.createFavorite(favoriteDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRecipeId()).isEqualTo(1L);
        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void createFavorite_ShouldThrowException_WhenUserNotAuthenticated() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> favoriteService.createFavorite(favoriteDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    void deleteFavorite_ShouldDeleteFavorite_WhenAuthorized() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        doNothing().when(favoriteRepository).deleteByUserIdAndRecipeId(1L, 1L);

        // Act
        favoriteService.deleteFavorite(1L, 1L);

        // Assert
        verify(favoriteRepository, times(1)).deleteByUserIdAndRecipeId(1L, 1L);
    }

    @Test
    void deleteFavorite_ShouldThrowException_WhenUnauthorized() {
        // Arrange
        User differentUser = new User();
        differentUser.setId(2L);
        differentUser.setEmail("other@example.com");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("other@example.com");
        when(userRepository.findByEmail("other@example.com")).thenReturn(Optional.of(differentUser));

        // Act & Assert
        assertThatThrownBy(() -> favoriteService.deleteFavorite(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No autorizado");
    }

    @Test
    void getAllFavorites_ShouldFilterByCurrentUser() {
        // Arrange
        Favorite otherUserFavorite = new Favorite();
        FavoriteKey otherKey = new FavoriteKey(2L, 2L);
        otherUserFavorite.setId(otherKey);
        otherUserFavorite.setCreatedAt(LocalDateTime.now());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(favorite, otherUserFavorite));

        // Act
        List<FavoriteDTO> result = favoriteService.getAllFavorites();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
    }
}
