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

    List<MealPlanItem> findByMealPlanId(Long mealPlanId);

    @Query("SELECT mpi FROM MealPlanItem mpi WHERE mpi.mealPlan.id = :mealPlanId " +
           "AND mpi.date BETWEEN :startDate AND :endDate")
    List<MealPlanItem> findByMealPlanIdAndDateRange(
        @Param("mealPlanId") Long mealPlanId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("SELECT mpi FROM MealPlanItem mpi WHERE mpi.mealPlan.user.id = :userId " +
           "AND mpi.date BETWEEN :startDate AND :endDate")
    List<MealPlanItem> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    Optional<MealPlanItem> findByMealPlanIdAndDateAndMealTypeId(
        Long mealPlanId, 
        LocalDate date, 
        Integer mealTypeId
    );

    void deleteByMealPlanIdAndDate(Long mealPlanId, LocalDate date);

    void deleteByMealPlanIdAndDateAndMealTypeId(
        Long mealPlanId, 
        LocalDate date, 
        Integer mealTypeId
    );
}
