package com.MealMate.MealMateBackend.user.repository;

import com.MealMate.MealMateBackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}