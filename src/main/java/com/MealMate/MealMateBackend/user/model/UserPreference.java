package com.MealMate.MealMateBackend.user.model;

   import jakarta.persistence.*;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;

   import java.math.BigDecimal;

   @Entity
   @Table(name = "user_preferences")
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class UserPreference {
       @Id
       private Long userId;

       @Column(name = "daily_calories_goal")
       private Integer dailyCaloriesGoal;

       @Column(name = "daily_carbs_goal", precision = 6, scale = 2)
       private BigDecimal dailyCarbsGoal;

       @Column(name = "daily_protein_goal", precision = 6, scale = 2)
       private BigDecimal dailyProteinGoal;

       @Column(name = "daily_fat_goal", precision = 6, scale = 2)
       private BigDecimal dailyFatGoal;

       @ManyToOne
       @JoinColumn(name = "diet_id")
       private Diet diet;
   }