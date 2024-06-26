package com.mealproject.mealplanner17.API;


import android.content.Context;

import com.mealproject.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.mealproject.mealplanner17.Listeners.RecipeDetailsListener;
import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.mealproject.mealplanner17.ModelsAPI.RecipeDetailsResponse;

import com.mealproject.mealplanner17.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The RequestManager class handles API requests related to recipes.
 * It provides methods to fetch random recipes and detailed information about a specific recipe.
 * This class uses Retrofit library to make network calls.
 * API documentation - https://spoonacular.com/food-api/docs#Get-Random-Recipes
 */
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
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
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

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags,
                @Query("tagsExclude") List<String> exTags // Change the parameter name to match the API endpoint
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey

        );
    }






}
