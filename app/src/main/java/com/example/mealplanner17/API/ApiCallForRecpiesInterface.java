package com.example.mealplanner17.API;

import com.example.mealplanner17.ModelsAPI.InstructionResponse;
import java.util.List;
import com.example.mealplanner17.ModelsAPI.RecipeDetailsResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallForRecpiesInterface {

    @GET("{id}/analyzedInstructions")
    Call<List<InstructionResponse>> getInstructions(@Path("id") int id, @Query("apiKey") String apiKey);

    // Method to fetch random recipes
    @GET("recipes/random")
    Call<List<RecipeDetailsResponse>> getRandomRecipes(
            @Query("tags") String tags,
            @Query("apiKey") String apiKey,
            @Query("number") int number
    );
}