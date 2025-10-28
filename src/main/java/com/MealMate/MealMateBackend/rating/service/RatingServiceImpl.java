package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import com.MealMate.MealMateBackend.rating.model.Rating;
import com.MealMate.MealMateBackend.rating.model.RatingKey;
import com.MealMate.MealMateBackend.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RatingDTO getRatingById(Long recipeId, Long userId) {
        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        return mapToDTO(rating);
    }

    @Override
    public RatingDTO createRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        RatingKey key = new RatingKey(ratingDTO.getRecipeId(), ratingDTO.getUserId());
        rating.setId(key);
        rating.setScore(ratingDTO.getScore());
        
        Rating savedRating = ratingRepository.save(rating);
        return mapToDTO(savedRating);
    }

    @Override
    public RatingDTO updateRating(Long recipeId, Long userId, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        rating.setScore(ratingDTO.getScore());
        Rating updatedRating = ratingRepository.save(rating);
        return mapToDTO(updatedRating);
    }

    @Override
    public void deleteRating(Long recipeId, Long userId) {
        ratingRepository.deleteByRecipeIdAndUserId(recipeId, userId);
    }

    // Método auxiliar para mapear manualmente
    private RatingDTO mapToDTO(Rating rating) {
        RatingDTO dto = new RatingDTO();
        dto.setRecipeId(rating.getId().getRecipeId());
        dto.setUserId(rating.getId().getUserId());
        dto.setScore(rating.getScore());
        return dto;
    }
}