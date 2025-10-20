package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import com.MealMate.MealMateBackend.rating.model.Rating;
import com.MealMate.MealMateBackend.rating.repository.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(rating -> modelMapper.map(rating, RatingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RatingDTO getRatingById(Long recipeId, Long userId) {
        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, userId).orElseThrow(() -> new RuntimeException("Rating not found"));
        return modelMapper.map(rating, RatingDTO.class);
    }

    @Override
    public RatingDTO createRating(RatingDTO ratingDTO) {
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        Rating savedRating = ratingRepository.save(rating);
        return modelMapper.map(savedRating, RatingDTO.class);
    }

    @Override
    public RatingDTO updateRating(Long recipeId, Long userId, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findByRecipeIdAndUserId(recipeId, userId).orElseThrow(() -> new RuntimeException("Rating not found"));
        modelMapper.map(ratingDTO, rating);
        Rating updatedRating = ratingRepository.save(rating);
        return modelMapper.map(updatedRating, RatingDTO.class);
    }

    @Override
    public void deleteRating(Long recipeId, Long userId) {
        ratingRepository.deleteByRecipeIdAndUserId(recipeId, userId);
    }
}