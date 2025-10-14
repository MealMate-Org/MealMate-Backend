package com.MealMate.MealMateBackend.rating.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @EmbeddedId
    private RatingKey id;

    @Column
    private Integer score;
}