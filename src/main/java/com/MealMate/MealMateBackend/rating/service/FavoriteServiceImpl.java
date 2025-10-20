package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.model.Favorite;
import com.MealMate.MealMateBackend.rating.repository.FavoriteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        return favoriteRepository.findAll().stream()
                .map(favorite -> modelMapper.map(favorite, FavoriteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FavoriteDTO getFavoriteById(Long userId, Long recipeId) {
        Favorite favorite = favoriteRepository.findByUserIdAndRecipeId(userId, recipeId).orElseThrow(() -> new RuntimeException("Favorite not found"));
        return modelMapper.map(favorite, FavoriteDTO.class);
    }

    @Override
    public FavoriteDTO createFavorite(FavoriteDTO favoriteDTO) {
        Favorite favorite = modelMapper.map(favoriteDTO, Favorite.class);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        return modelMapper.map(savedFavorite, FavoriteDTO.class);
    }

    @Override
    public void deleteFavorite(Long userId, Long recipeId) {
        favoriteRepository.deleteByUserIdAndRecipeId(userId, recipeId);
    }
}