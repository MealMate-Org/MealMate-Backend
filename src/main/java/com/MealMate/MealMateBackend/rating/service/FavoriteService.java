package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import java.util.List;

public interface FavoriteService {
    List<FavoriteDTO> getAllFavorites();
    FavoriteDTO getFavoriteById(Long userId, Long recipeId);
    FavoriteDTO createFavorite(FavoriteDTO favoriteDTO);
    void deleteFavorite(Long userId, Long recipeId);
}