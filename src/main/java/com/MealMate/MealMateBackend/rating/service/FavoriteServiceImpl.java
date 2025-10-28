package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.model.Favorite;
import com.MealMate.MealMateBackend.rating.model.FavoriteKey;
import com.MealMate.MealMateBackend.rating.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        try {
            List<Favorite> favorites = favoriteRepository.findAll();
            return favorites.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al obtener favoritos: " + e.getMessage());
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
            Favorite favorite = new Favorite();
            FavoriteKey key = new FavoriteKey(favoriteDTO.getUserId(), favoriteDTO.getRecipeId());
            favorite.setId(key);
            favorite.setCreatedAt(LocalDateTime.now());
            
            Favorite savedFavorite = favoriteRepository.save(favorite);
            return mapToDTO(savedFavorite);
        } catch (Exception e) {
            System.err.println("Error al crear favorito: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear favorito", e);
        }
    }

    @Override
    public void deleteFavorite(Long userId, Long recipeId) {
        try {
            favoriteRepository.deleteByUserIdAndRecipeId(userId, recipeId);
        } catch (Exception e) {
            System.err.println("Error al eliminar favorito: " + e.getMessage());
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