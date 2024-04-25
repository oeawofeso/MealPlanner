package com.mealproject.mealplanner17.Listeners;

import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;

/**
 * The RandomRecipeResponseListener interface provides callback methods to handle the results
 * of fetching random recipe responses from the API.
 */
public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
