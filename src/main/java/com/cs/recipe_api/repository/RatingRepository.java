package com.cs.recipe_api.repository;

import com.cs.recipe_api.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByRecipeIdAndUserId(Long recipeId, Long userId);
    boolean existsByRecipeIdAndUserId(Long recipeId, Long userId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.recipe.id = :recipeId")
    Double findAverageScoreByRecipeId(@Param("recipeId") Long recipeId);
}
