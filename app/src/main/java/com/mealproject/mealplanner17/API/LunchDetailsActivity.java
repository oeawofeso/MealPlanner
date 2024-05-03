package com.mealproject.mealplanner17.API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mealproject.mealplanner17.Adapters.IngredientsAdapter;

import com.mealproject.mealplanner17.Adapters.InstructionAdapter;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Breakfast.BreakfastSidesActivity;
import com.mealproject.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.Listeners.RecipeDetailsListener;
import com.mealproject.mealplanner17.ModelsAPI.InstructionResponse;
import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.mealproject.mealplanner17.ModelsAPI.RecipeDetailsResponse;
import com.mealproject.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The RecipeDetailsActivity class displays detailed information about a specific meal.
 * It includes the meal's name, source of information, ingredients, recipe, and side dishes.
 * This activity fetches data from the Spoonacular API to populate its views.
 * https://www.youtube.com/watch?v=V20Mj4w-7K4
 * The video shows how Spoonacular API works and how to request and read response
 * Spoonacular API Documentation:
 * https://spoonacular.com/food-api/docs#Get-Random-Recipes
 * This documentation was used to understand the API response structure and display the data to the user.
 *
 * Learn how to extract data from json response through
 * https://www.geeksforgeeks.org/how-to-extract-data-from-json-array-in-android-using-retrofit-library/?ref=ml_lbp
 * Last Updated : 08 Dec, 2021 author: geeksforgeeks
 *
 */
public class LunchDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name,textView_meal_source;
    ImageView imageView_meal_image,imageView_ingredients;
    RecyclerView recycler_meal_ingredients,recipeStepsRecylerView;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    Button btnFavorite;

    Button nextRecipeButton;
    RecyclerView randomMealsRecyclerView;
    RecyclerView sidesRecyclerView;
    RandomRecipeAdapter randomRecipeAdapter;




    private static final String TAG = "LunchDetailsActivity";
    private final static String API_KEY = "3ef8f95c17944ed6bac930281b8b453e";
    ApiCallForRecpiesInterface APIgetRecipes;
    List<InstructionResponse> recipeSteps;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_details); // Set the layout file here
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        // Initialize views...
        initObjects();

        // Get recipe ID from intent...
        id = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);

        // Hide the next recipe button...
        nextRecipeButton = findViewById(R.id.button_next_recipe);
        nextRecipeButton.setVisibility(View.GONE);

        // Set up RecyclerView for recipe steps...
        recipeStepsRecylerView = findViewById(R.id.stepList);

        // Show lunch sides at the bottom of the layout...
        showLunchSides();

        // Fetch and display drinks for breakfast sides...
    }


    private void initObjects() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);

        imageView_meal_image= findViewById(R.id.imageView_meal_image);
        imageView_ingredients = findViewById(R.id.imageView_ingredients);
        recycler_meal_ingredients= findViewById(R.id.recycler_meal_ingredients);
        sidesRecyclerView = findViewById(R.id.sides_recycler_view);
    }
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);


            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(LunchDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));

            ingredientsAdapter = new IngredientsAdapter(LunchDetailsActivity.this,response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

            APIgetRecipes = ApiCallForRecpies.getClient().create(ApiCallForRecpiesInterface.class);
            getInstructionsForRecipe(id);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(LunchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };






    private void getInstructionsForRecipe(int id) {
        Call<List<InstructionResponse>> call = APIgetRecipes.getInstructions(id,API_KEY);
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call <List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {

                assert response.body() != null;
                recipeSteps = response.body();

                for (int i = 0; i< recipeSteps.get(0).getSteps().size(); i++){
                    Log.d(TAG, "Step: Number "+ recipeSteps.get(0).getSteps().get(i).getNumber()+" " + recipeSteps.get(0).getSteps().get(i).getStep());
                }
                recipeStepsRecylerView.setLayoutManager(new LinearLayoutManager( getApplicationContext()));
                recipeStepsRecylerView.setAdapter(new InstructionAdapter(recipeSteps.get(0).getSteps(), R.layout.eachsteps, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
    private void showLunchSides() {
        // Create a RandomRecipeAdapter for lunch sides
        RandomRecipeAdapter lunchSidesAdapter = new RandomRecipeAdapter(this, new ArrayList<>(), new RecipeClickListener() {
            @Override
            public void onRecipeClick(String recipeId) {
                // Handle lunch side recipe click event (e.g., open another activity)
                startActivity(new Intent(LunchDetailsActivity.this, LunchDetailsActivity.class)
                        .putExtra("id", recipeId));
            }
        });

        // Set up the RecyclerView for lunch sides
        sidesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sidesRecyclerView.setAdapter(lunchSidesAdapter);

        // Call the getRandomRecipes method of RequestManager to fetch lunch sides
        manager.getRandomRecipes(new RandomRecipeResponseListener() {
            @Override
            public void didFetch(RandomRecipeApiResponse response, String message) {
                // Update the adapter with the fetched lunch sides recipes
                lunchSidesAdapter.updateData(response.recipes);
            }

            @Override
            public void didError(String message) {
                Toast.makeText(LunchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, getLunchSidesTags(), new ArrayList<>());
    }
    private List<String> getLunchSidesTags() {
        List<String> lunchSidesTags = new ArrayList<>();
        lunchSidesTags.add("side dish"); // Add appropriate tags for lunch sides
        return lunchSidesTags;
    }


}