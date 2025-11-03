package com.MealMate.MealMateBackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceDTO {
    private Long userId;
    
    private Integer dailyCaloriesGoal;
    private BigDecimal dailyCarbsGoal;
    private BigDecimal dailyProteinGoal;
    private BigDecimal dailyFatGoal;
    private Integer dietId;
    
    private Boolean useAutomaticCalculation;
    private String gender;
    private Integer age;
    private BigDecimal weight;
    private BigDecimal height;
    private String activityLevel;
    private String goal;
}
