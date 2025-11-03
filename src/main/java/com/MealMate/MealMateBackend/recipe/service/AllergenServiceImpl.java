package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import com.MealMate.MealMateBackend.recipe.model.Allergen;
import com.MealMate.MealMateBackend.recipe.repository.AllergenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllergenServiceImpl implements AllergenService {

    @Autowired
    private AllergenRepository allergenRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AllergenDTO> getAllAllergens() {
        return allergenRepository.findAll().stream()
                .map(allergen -> modelMapper.map(allergen, AllergenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AllergenDTO getAllergenById(Integer id) {
        Allergen allergen = allergenRepository.findById(id).orElseThrow(() -> new RuntimeException("Allergen not found"));
        return modelMapper.map(allergen, AllergenDTO.class);
    }

    @Override
    public AllergenDTO createAllergen(AllergenDTO allergenDTO) {
        Allergen allergen = modelMapper.map(allergenDTO, Allergen.class);
        Allergen savedAllergen = allergenRepository.save(allergen);
        return modelMapper.map(savedAllergen, AllergenDTO.class);
    }

    @Override
    public AllergenDTO updateAllergen(Integer id, AllergenDTO allergenDTO) {
        Allergen allergen = allergenRepository.findById(id).orElseThrow(() -> new RuntimeException("Allergen not found"));
        modelMapper.map(allergenDTO, allergen);
        Allergen updatedAllergen = allergenRepository.save(allergen);
        return modelMapper.map(updatedAllergen, AllergenDTO.class);
    }

    @Override
    public void deleteAllergen(Integer id) {
        allergenRepository.deleteById(id);
    }
}