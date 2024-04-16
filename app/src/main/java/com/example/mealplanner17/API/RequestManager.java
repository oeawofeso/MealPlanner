package com.example.mealplanner17.API;


import android.content.Context;

import com.example.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.example.mealplanner17.Listeners.RecipeDetailsListener;
import com.example.mealplanner17.ModelsAPI.InstructionResponse;
import com.example.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.example.mealplanner17.ModelsAPI.RecipeDetailsResponse;

import com.example.mealplanner17.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RequestManager {
    Context context;
    Retrofit retrofit= new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String>  tags,List<String> exTags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call= callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"3",tags,exTags);
        call.enqueue(new Callback<RandomRecipeApiResponse>(){

            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getRecipeDetails(RecipeDetailsListener listener,int id){
        CallRecipeDetails callRecipeDetails= retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call= callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(

                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags,
                @Query("tags") List<String> exTags

        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey

        );
    }
    public void getJuiceOptions(RandomRecipeResponseListener listener, List<String> tags) {
        // Create an instance of the CallRandomRecipes interface
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);

        // Make an API call with the "juice" tags
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(
                context.getString(R.string.api_key), // API key
                "3", // Number of recipes to fetch
                tags, // Include tags (e.g., "juice")
                null // No exclusion tags
        );

        // Enqueue the call and handle the response
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }







}
