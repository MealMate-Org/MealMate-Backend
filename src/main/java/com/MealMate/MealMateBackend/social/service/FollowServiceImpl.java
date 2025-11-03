package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.FollowDTO;
import com.MealMate.MealMateBackend.social.model.Follow;
import com.MealMate.MealMateBackend.social.repository.FollowRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FollowDTO> getAllFollows() {
        return followRepository.findAll().stream()
                .map(follow -> modelMapper.map(follow, FollowDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FollowDTO getFollowById(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followingId).orElseThrow(() -> new RuntimeException("Follow not found"));
        return modelMapper.map(follow, FollowDTO.class);
    }

    @Override
    public FollowDTO createFollow(FollowDTO followDTO) {
        Follow follow = modelMapper.map(followDTO, Follow.class);
        Follow savedFollow = followRepository.save(follow);
        return modelMapper.map(savedFollow, FollowDTO.class);
    }

    @Override
    public void deleteFollow(Long followerId, Long followingId) {
        followRepository.deleteByFollowerIdAndFollowedId(followerId, followingId);
    }
}