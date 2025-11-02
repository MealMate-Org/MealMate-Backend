package com.MealMate.MealMateBackend.shopping.repository;

import com.MealMate.MealMateBackend.shopping.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findByUserId(Long userId);
    List<ShoppingList> findByMealPlanId(Long mealPlanId);
    Optional<ShoppingList> findByUserIdAndWeekStartDateAndWeekEndDate(Long userId, java.time.LocalDate weekStartDate, java.time.LocalDate weekEndDate);
}