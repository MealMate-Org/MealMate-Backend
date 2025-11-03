package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.UserPreferenceDTO;

public interface UserPreferenceService {
    UserPreferenceDTO getUserPreferenceByUserId(Long userId);
    UserPreferenceDTO createOrUpdateUserPreference(UserPreferenceDTO userPreferenceDTO);
    void deleteUserPreference(Long userId);
}