package com.MealMate.MealMateBackend.recipe.repository;

import com.MealMate.MealMateBackend.recipe.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Integer> {
}