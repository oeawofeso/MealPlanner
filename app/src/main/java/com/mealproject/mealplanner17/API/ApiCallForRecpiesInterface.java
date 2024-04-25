package com.mealproject.mealplanner17.API;

import com.mealproject.mealplanner17.ModelsAPI.InstructionResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for the api call in order to get the recipe instructions based on each id of meal
 * https://spoonacular.com/food-api/docs#Get-Analyzed-Recipe-Instructions
 */
public interface ApiCallForRecpiesInterface {

    @GET("{id}/analyzedInstructions")
    Call<List<InstructionResponse>> getInstructions(@Path("id") int id, @Query("apiKey") String apiKey);


}