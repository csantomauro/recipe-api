package com.cs.recipe_api.repository;

import com.cs.recipe_api.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);
    boolean existsByName(String name);
}