package com.MealMate.MealMateBackend.rating.repository;

import com.MealMate.MealMateBackend.rating.model.Favorite;
import com.MealMate.MealMateBackend.rating.model.FavoriteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteKey> {
    
    @Query("SELECT f FROM Favorite f WHERE f.id.userId = :userId AND f.id.recipeId = :recipeId")
    Optional<Favorite> findByUserIdAndRecipeId(@Param("userId") Long userId, @Param("recipeId") Long recipeId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.id.userId = :userId AND f.id.recipeId = :recipeId")
    void deleteByUserIdAndRecipeId(@Param("userId") Long userId, @Param("recipeId") Long recipeId);
}