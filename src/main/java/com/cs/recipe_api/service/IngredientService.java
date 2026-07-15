package com.cs.recipe_api.service;

import com.cs.recipe_api.model.Ingredient;
import com.cs.recipe_api.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findOrCreate(String name) {
        return ingredientRepository.findByName(name)
                .orElseGet(() -> {
                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setName(name);
                    return ingredientRepository.save(newIngredient);
                });
    }
}
