package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.FollowDTO;
import java.util.List;

public interface FollowService {
    List<FollowDTO> getAllFollows();
    FollowDTO getFollowById(Long followerId, Long followingId);
    FollowDTO createFollow(FollowDTO followDTO);
    void deleteFollow(Long followerId, Long followingId);
}