package com.MealMate.MealMateBackend.social.repository;

import com.MealMate.MealMateBackend.social.model.Follow;
import com.MealMate.MealMateBackend.social.model.FollowKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowKey> {
    
    @Query("SELECT f FROM Follow f WHERE f.id.followerId = :followerId AND f.id.followedId = :followedId")
    Optional<Follow> findByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.id.followerId = :followerId AND f.id.followedId = :followedId")
    void deleteByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);
}