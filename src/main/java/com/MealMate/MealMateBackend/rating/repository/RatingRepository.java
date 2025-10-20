package com.MealMate.MealMateBackend.rating.repository;

import com.MealMate.MealMateBackend.rating.model.Rating;
import com.MealMate.MealMateBackend.rating.model.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    
    @Query("SELECT r FROM Rating r WHERE r.id.recipeId = :recipeId AND r.id.userId = :userId")
    Optional<Rating> findByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Rating r WHERE r.id.recipeId = :recipeId AND r.id.userId = :userId")
    void deleteByRecipeIdAndUserId(@Param("recipeId") Long recipeId, @Param("userId") Long userId);
}
