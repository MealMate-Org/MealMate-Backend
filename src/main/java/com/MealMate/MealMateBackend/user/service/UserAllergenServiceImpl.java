package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import com.MealMate.MealMateBackend.recipe.model.Allergen;
import com.MealMate.MealMateBackend.recipe.repository.AllergenRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.model.UserAllergen;
import com.MealMate.MealMateBackend.user.repository.UserAllergenRepository;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAllergenServiceImpl implements UserAllergenService {

    @Autowired
    private UserAllergenRepository userAllergenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllergenRepository allergenRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AllergenDTO> getUserAllergens(Long userId) {
        List<UserAllergen> userAllergens = userAllergenRepository.findByUserId(userId);
        return userAllergens.stream()
                .map(ua -> modelMapper.map(ua.getAllergen(), AllergenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveUserAllergens(Long userId, List<Integer> allergenIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userAllergenRepository.deleteByUserId(userId);

        if (allergenIds != null && !allergenIds.isEmpty()) {
            List<Allergen> allergens = allergenRepository.findAllById(allergenIds);
            List<UserAllergen> userAllergens = allergens.stream()
                    .map(allergen -> {
                        UserAllergen ua = new UserAllergen();
                        ua.setUser(user);
                        ua.setAllergen(allergen);
                        return ua;
                    })
                    .collect(Collectors.toList());
            userAllergenRepository.saveAll(userAllergens);
        }
    }

    @Override
    @Transactional
    public void deleteAllUserAllergens(Long userId) {
        userAllergenRepository.deleteByUserId(userId);
    }
}