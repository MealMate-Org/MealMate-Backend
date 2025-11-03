package com.MealMate.MealMateBackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String username;
    private String email;
    private String password;
    private String avatar;
    private String bio;
    private Integer roleId;
}