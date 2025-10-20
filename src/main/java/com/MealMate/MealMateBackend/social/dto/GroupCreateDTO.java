package com.MealMate.MealMateBackend.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDTO {
    private String name;
    private String description;
    private Long createdBy;
}