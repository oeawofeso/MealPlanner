package com.example.mealplanner17.Listeners;

import com.example.mealplanner17.ModelsAPI.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
