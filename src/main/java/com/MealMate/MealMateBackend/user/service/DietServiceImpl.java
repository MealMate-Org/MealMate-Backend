package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.DietDTO;
import com.MealMate.MealMateBackend.user.model.Diet;
import com.MealMate.MealMateBackend.user.repository.DietRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DietDTO> getAllDiets() {
        return dietRepository.findAll().stream()
                .map(diet -> modelMapper.map(diet, DietDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DietDTO getDietById(Integer id) {
        Diet diet = dietRepository.findById(id).orElseThrow(() -> new RuntimeException("Diet not found"));
        return modelMapper.map(diet, DietDTO.class);
    }

    @Override
    public DietDTO createDiet(DietDTO dietDTO) {
        Diet diet = modelMapper.map(dietDTO, Diet.class);
        Diet savedDiet = dietRepository.save(diet);
        return modelMapper.map(savedDiet, DietDTO.class);
    }

    @Override
    public DietDTO updateDiet(Integer id, DietDTO dietDTO) {
        Diet diet = dietRepository.findById(id).orElseThrow(() -> new RuntimeException("Diet not found"));
        modelMapper.map(dietDTO, diet);
        Diet updatedDiet = dietRepository.save(diet);
        return modelMapper.map(updatedDiet, DietDTO.class);
    }

    @Override
    public void deleteDiet(Integer id) {
        dietRepository.deleteById(id);
    }
}