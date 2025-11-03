package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import java.util.List;

public interface RatingService {
    List<RatingDTO> getAllRatings();
    RatingDTO getRatingById(Long recipeId, Long userId);
    RatingDTO createRating(RatingDTO ratingDTO);
    RatingDTO updateRating(Long recipeId, Long userId, RatingDTO ratingDTO);
    void deleteRating(Long recipeId, Long userId);
}