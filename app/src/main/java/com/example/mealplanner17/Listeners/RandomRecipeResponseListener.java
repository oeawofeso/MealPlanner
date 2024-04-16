package com.example.mealplanner17.Listeners;

import com.example.mealplanner17.ModelsAPI.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
