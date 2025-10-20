package com.MealMate.MealMateBackend.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItem {
    private Long id;
    private String name;
    private BigDecimal quantity;
    private String unit;
    private Boolean checked;
}