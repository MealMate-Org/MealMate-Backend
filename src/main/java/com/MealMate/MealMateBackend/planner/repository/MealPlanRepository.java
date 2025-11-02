package com.MealMate.MealMateBackend.planner.repository;

import com.MealMate.MealMateBackend.planner.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    
    /**
     * Encuentra todos los planes de un usuario
     */
    List<MealPlan> findByUserId(Long userId);
    
    /**
     * Encuentra el plan activo de un usuario para una fecha específica
     */
    @Query("SELECT mp FROM MealPlan mp WHERE mp.user.id = :userId " +
           "AND :date BETWEEN mp.startDate AND mp.endDate " +
           "AND mp.isActive = true")
    Optional<MealPlan> findActiveByUserIdAndDate(
        @Param("userId") Long userId, 
        @Param("date") LocalDate date
    );
    
    /**
     * Encuentra el plan de un usuario que contiene un rango de fechas
     */
    @Query("SELECT mp FROM MealPlan mp WHERE mp.user.id = :userId " +
           "AND mp.startDate <= :endDate AND mp.endDate >= :startDate " +
           "AND mp.isActive = true")
    Optional<MealPlan> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    /**
     * Encuentra planes por usuario y rango de fechas
     */
    List<MealPlan> findByUserIdAndStartDateBetween(
        Long userId, 
        LocalDate startDate, 
        LocalDate endDate
    );
}
