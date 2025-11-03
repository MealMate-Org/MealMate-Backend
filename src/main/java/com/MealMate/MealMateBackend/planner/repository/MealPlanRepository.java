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

    List<MealPlan> findByUserId(Long userId);

    @Query("SELECT mp FROM MealPlan mp WHERE mp.user.id = :userId " +
           "AND :date BETWEEN mp.startDate AND mp.endDate " +
           "AND mp.isActive = true")
    Optional<MealPlan> findActiveByUserIdAndDate(
        @Param("userId") Long userId, 
        @Param("date") LocalDate date
    );

    @Query("SELECT mp FROM MealPlan mp WHERE mp.user.id = :userId " +
           "AND mp.startDate <= :endDate AND mp.endDate >= :startDate " +
           "AND mp.isActive = true")
    Optional<MealPlan> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<MealPlan> findByUserIdAndStartDateBetween(
        Long userId, 
        LocalDate startDate, 
        LocalDate endDate
    );
}
