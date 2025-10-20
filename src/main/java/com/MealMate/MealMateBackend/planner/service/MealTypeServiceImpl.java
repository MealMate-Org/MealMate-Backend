package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealTypeDTO;
import com.MealMate.MealMateBackend.planner.model.MealType;
import com.MealMate.MealMateBackend.planner.repository.MealTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealTypeServiceImpl implements MealTypeService {

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MealTypeDTO> getAllMealTypes() {
        return mealTypeRepository.findAll().stream()
                .map(mealType -> modelMapper.map(mealType, MealTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MealTypeDTO getMealTypeById(Integer id) {
        MealType mealType = mealTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("MealType not found"));
        return modelMapper.map(mealType, MealTypeDTO.class);
    }

    @Override
    public MealTypeDTO createMealType(MealTypeDTO mealTypeDTO) {
        MealType mealType = modelMapper.map(mealTypeDTO, MealType.class);
        MealType savedMealType = mealTypeRepository.save(mealType);
        return modelMapper.map(savedMealType, MealTypeDTO.class);
    }

    @Override
    public MealTypeDTO updateMealType(Integer id, MealTypeDTO mealTypeDTO) {
        MealType mealType = mealTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("MealType not found"));
        modelMapper.map(mealTypeDTO, mealType);
        MealType updatedMealType = mealTypeRepository.save(mealType);
        return modelMapper.map(updatedMealType, MealTypeDTO.class);
    }

    @Override
    public void deleteMealType(Integer id) {
        mealTypeRepository.deleteById(id);
    }
}