package com.MealMate.MealMateBackend.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListCreateDTO {
    private Long userId;
    private Long mealPlanId;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private String title;
    private List<ShoppingItemDTO> items;
}