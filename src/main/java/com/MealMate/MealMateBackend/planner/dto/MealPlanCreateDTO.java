package com.MealMate.MealMateBackend.planner.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanCreateDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
