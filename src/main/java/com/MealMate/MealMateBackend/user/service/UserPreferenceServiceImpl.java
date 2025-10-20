package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.UserPreferenceDTO;
import com.MealMate.MealMateBackend.user.model.UserPreference;
import com.MealMate.MealMateBackend.user.repository.UserPreferenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserPreferenceDTO getUserPreferenceByUserId(Long userId) {
        UserPreference preference = userPreferenceRepository.findById(userId).orElseThrow(() -> new RuntimeException("Preference not found"));
        return modelMapper.map(preference, UserPreferenceDTO.class);
    }

    @Override
    public UserPreferenceDTO createOrUpdateUserPreference(UserPreferenceDTO userPreferenceDTO) {
        UserPreference preference = modelMapper.map(userPreferenceDTO, UserPreference.class);
        UserPreference savedPreference = userPreferenceRepository.save(preference);
        return modelMapper.map(savedPreference, UserPreferenceDTO.class);
    }

    @Override
    public void deleteUserPreference(Long userId) {
        userPreferenceRepository.deleteById(userId);
    }
}