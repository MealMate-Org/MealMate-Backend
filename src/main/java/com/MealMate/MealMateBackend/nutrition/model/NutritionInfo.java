package com.MealMate.MealMateBackend.nutrition.model;

   import jakarta.persistence.*;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;

   import java.math.BigDecimal;

   @Entity
   @Table(name = "nutrition_info")
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class NutritionInfo {
       @Id
       private Long recipeId;

       @Column(precision = 6, scale = 2)
       private BigDecimal calories;

       @Column(precision = 6, scale = 2)
       private BigDecimal protein;

       @Column(precision = 6, scale = 2)
       private BigDecimal carbs;

       @Column(precision = 6, scale = 2)
       private BigDecimal fat;

       @Column(precision = 10, scale = 2)
       private BigDecimal portionSize;
   }