package com.MealMate.MealMateBackend.social.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    @EmbeddedId
    private GroupMemberKey id;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'member'")
    private String role;
}