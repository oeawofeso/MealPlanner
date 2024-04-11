package com.example.mealplanner17.API;

import com.example.mealplanner17.Models.InstructionResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallForRecpiesInterface {

    @GET("{id}/analyzedInstructions")
    Call<List<InstructionResponse>> getInstructions(@Path("id") int id, @Query("apiKey") String apiKey);


}