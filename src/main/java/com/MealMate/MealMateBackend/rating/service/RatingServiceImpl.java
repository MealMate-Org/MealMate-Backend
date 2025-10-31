package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import com.MealMate.MealMateBackend.rating.model.Rating;
import com.MealMate.MealMateBackend.rating.model.RatingKey;
import com.MealMate.MealMateBackend.rating.repository.RatingRepository;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;

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
    @Transactional
    public RatingDTO createRating(RatingDTO ratingDTO) {
        System.out.println("📊 Creando rating: recipeId=" + ratingDTO.getRecipeId() + 
                         ", userId=" + ratingDTO.getUserId() + 
                         ", score=" + ratingDTO.getScore());
        
        // Verificar si ya existe un rating
        Rating rating = ratingRepository.findByRecipeIdAndUserId(
            ratingDTO.getRecipeId(), 
            ratingDTO.getUserId()
        ).orElse(null);
        
        if (rating != null) {
            // Si ya existe, actualizamos
            System.out.println("⚠️ Rating ya existe, actualizando...");
            rating.setScore(ratingDTO.getScore());
        } else {
            // Si no existe, creamos nuevo
            rating = new Rating();
            RatingKey key = new RatingKey(ratingDTO.getRecipeId(), ratingDTO.getUserId());
            rating.setId(key);
            rating.setScore(ratingDTO.getScore());
        }
        
        Rating savedRating = ratingRepository.save(rating);
        
        // Actualizar avgRating y ratingCount de la receta
        updateRecipeRatings(ratingDTO.getRecipeId());
        
        return mapToDTO(savedRating);
    }

    @Override
    @Transactional
    public RatingDTO updateRating(Long recipeId, Long userId, RatingDTO ratingDTO) {
        System.out.println("📊 Actualizando rating: recipeId=" + recipeId + 
                         ", userId=" + userId + 
                         ", score=" + ratingDTO.getScore());
        
        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        
        rating.setScore(ratingDTO.getScore());
        Rating updatedRating = ratingRepository.save(rating);
        
        // Actualizar avgRating y ratingCount de la receta
        updateRecipeRatings(recipeId);
        
        return mapToDTO(updatedRating);
    }

    @Override
    @Transactional
    public void deleteRating(Long recipeId, Long userId) {
        System.out.println("🗑️ Eliminando rating: recipeId=" + recipeId + ", userId=" + userId);
        
        ratingRepository.deleteByRecipeIdAndUserId(recipeId, userId);
        
        // Actualizar avgRating y ratingCount de la receta
        updateRecipeRatings(recipeId);
    }

    /**
     * Recalcula y actualiza el avgRating y ratingCount de una receta
     */
    private void updateRecipeRatings(Long recipeId) {
        System.out.println("🔄 Recalculando ratings para receta " + recipeId);
        
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        
        // Obtener todos los ratings de esta receta usando consulta optimizada
        List<Rating> ratings = ratingRepository.findByRecipeId(recipeId);
        
        int ratingCount = ratings.size();
        BigDecimal avgRating;
        
        if (ratingCount > 0) {
            // Calcular promedio
            double sum = ratings.stream()
                    .mapToInt(Rating::getScore)
                    .sum();
            
            avgRating = BigDecimal.valueOf(sum / ratingCount)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            avgRating = BigDecimal.ZERO;
        }
        
        // Actualizar receta
        recipe.setRatingCount(ratingCount);
        recipe.setAvgRating(avgRating);
        recipeRepository.save(recipe);
        
        System.out.println("✅ Ratings actualizados: avgRating=" + avgRating + 
                         ", ratingCount=" + ratingCount);
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