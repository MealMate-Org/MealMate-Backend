package com.MealMate.MealMateBackend.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItemDTO {
    private String name;
    private Double quantity;
    private String unit;
    private Boolean checked = false;
}