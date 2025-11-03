package com.MealMate.MealMateBackend.social.controller;

import com.MealMate.MealMateBackend.social.dto.FollowDTO;
import com.MealMate.MealMateBackend.social.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping
    public ResponseEntity<List<FollowDTO>> getAllFollows() {
        return ResponseEntity.ok(followService.getAllFollows());
    }

    @GetMapping("/{followerId}/{followingId}")
    public ResponseEntity<FollowDTO> getFollowById(@PathVariable Long followerId, @PathVariable Long followingId) {
        return ResponseEntity.ok(followService.getFollowById(followerId, followingId));
    }

    @PostMapping
    public ResponseEntity<FollowDTO> createFollow(@RequestBody FollowDTO followDTO) {
        return ResponseEntity.ok(followService.createFollow(followDTO));
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.deleteFollow(followerId, followingId);
        return ResponseEntity.noContent().build();
    }
}