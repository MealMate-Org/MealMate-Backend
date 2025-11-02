package com.MealMate.MealMateBackend.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanDTO {
    private Long id;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private Boolean isActive;
}
