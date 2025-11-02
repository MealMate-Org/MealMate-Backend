package com.MealMate.MealMateBackend.planner.repository;

import com.MealMate.MealMateBackend.planner.model.MealPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealPlanItemRepository extends JpaRepository<MealPlanItem, Long> {
    
    /**
     * Obtiene todos los items de un meal plan
     */
    List<MealPlanItem> findByMealPlanId(Long mealPlanId);
    
    /**
     * Obtiene items de un meal plan en un rango de fechas
     */
    @Query("SELECT mpi FROM MealPlanItem mpi WHERE mpi.mealPlan.id = :mealPlanId " +
           "AND mpi.date BETWEEN :startDate AND :endDate")
    List<MealPlanItem> findByMealPlanIdAndDateRange(
        @Param("mealPlanId") Long mealPlanId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    /**
     * Obtiene items de un usuario en un rango de fechas
     */
    @Query("SELECT mpi FROM MealPlanItem mpi WHERE mpi.mealPlan.user.id = :userId " +
           "AND mpi.date BETWEEN :startDate AND :endDate")
    List<MealPlanItem> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    /**
     * Encuentra un item específico por meal plan, fecha y tipo de comida
     */
    Optional<MealPlanItem> findByMealPlanIdAndDateAndMealTypeId(
        Long mealPlanId, 
        LocalDate date, 
        Integer mealTypeId
    );
    
    /**
     * Elimina todos los items de un meal plan en una fecha específica
     */
    void deleteByMealPlanIdAndDate(Long mealPlanId, LocalDate date);
    
    /**
     * Elimina un item específico por meal plan, fecha y tipo de comida
     */
    void deleteByMealPlanIdAndDateAndMealTypeId(
        Long mealPlanId, 
        LocalDate date, 
        Integer mealTypeId
    );
}
