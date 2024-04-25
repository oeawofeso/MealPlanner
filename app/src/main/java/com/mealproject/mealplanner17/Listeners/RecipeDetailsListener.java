package com.mealproject.mealplanner17.Listeners;

import com.mealproject.mealplanner17.ModelsAPI.RecipeDetailsResponse;

/**
 * The RecipeDetailsListener interface provides callback methods to handle the results
 * of fetching recipe details from the API.
 */
public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
