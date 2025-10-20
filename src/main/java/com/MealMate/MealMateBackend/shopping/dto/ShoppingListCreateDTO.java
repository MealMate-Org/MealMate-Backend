package com.MealMate.MealMateBackend.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListCreateDTO {
    private Long mealPlanId;
    private Long groupId;
    private Long userId;
    private List<ShoppingItem> items;
}