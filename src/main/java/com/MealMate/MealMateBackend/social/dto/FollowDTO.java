package com.MealMate.MealMateBackend.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    private Long followerId;
    private Long followingId;
    private LocalDateTime createdAt;
}