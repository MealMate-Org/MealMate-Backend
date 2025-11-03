package com.MealMate.MealMateBackend.social.repository;

import com.MealMate.MealMateBackend.social.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}