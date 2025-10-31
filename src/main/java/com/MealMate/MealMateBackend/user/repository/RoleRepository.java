package com.MealMate.MealMateBackend.user.repository;

import com.MealMate.MealMateBackend.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}