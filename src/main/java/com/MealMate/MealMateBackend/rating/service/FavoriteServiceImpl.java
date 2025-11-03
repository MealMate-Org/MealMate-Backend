package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.model.Favorite;
import com.MealMate.MealMateBackend.rating.model.FavoriteKey;
import com.MealMate.MealMateBackend.rating.repository.FavoriteRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            System.out.println("🔍 Cargando favoritos del usuario: " + email + " (ID: " + currentUser.getId() + ")");

            List<Favorite> favorites = favoriteRepository.findAll();
            List<FavoriteDTO> userFavorites = favorites.stream()
                    .filter(f -> f.getId().getUserId().equals(currentUser.getId()))
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            System.out.println("✅ Favoritos encontrados: " + userFavorites.size());
            return userFavorites;
        } catch (Exception e) {
            System.err.println("❌ Error al obtener favoritos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener favoritos", e);
        }
    }

    @Override
    public FavoriteDTO getFavoriteById(Long userId, Long recipeId) {
        Favorite favorite = favoriteRepository.findByUserIdAndRecipeId(userId, recipeId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        return mapToDTO(favorite);
    }

    @Override
    public FavoriteDTO createFavorite(FavoriteDTO favoriteDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Favorite favorite = new Favorite();
            FavoriteKey key = new FavoriteKey(currentUser.getId(), favoriteDTO.getRecipeId());
            favorite.setId(key);
            favorite.setCreatedAt(LocalDateTime.now());

            Favorite savedFavorite = favoriteRepository.save(favorite);
            System.out.println(
                    "✅ Favorito creado: Usuario " + currentUser.getId() + " -> Receta " + favoriteDTO.getRecipeId());
            return mapToDTO(savedFavorite);
        } catch (Exception e) {
            System.err.println("❌ Error al crear favorito: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear favorito", e);
        }
    }

    @Override
    public void deleteFavorite(Long userId, Long recipeId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!currentUser.getId().equals(userId)) {
                throw new RuntimeException("No autorizado para eliminar este favorito");
            }

            favoriteRepository.deleteByUserIdAndRecipeId(userId, recipeId);
            System.out.println("✅ Favorito eliminado: Usuario " + userId + " -> Receta " + recipeId);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar favorito: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar favorito", e);
        }
    }

    private FavoriteDTO mapToDTO(Favorite favorite) {
        if (favorite == null || favorite.getId() == null) {
            return null;
        }
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(favorite.getId().getUserId());
        dto.setRecipeId(favorite.getId().getRecipeId());
        dto.setCreatedAt(favorite.getCreatedAt());
        return dto;
    }
}