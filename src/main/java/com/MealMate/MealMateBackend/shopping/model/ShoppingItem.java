package com.MealMate.MealMateBackend.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItem {
    private String name;
    private Double quantity;
    private String unit;
    private Boolean isChecked = false;
}