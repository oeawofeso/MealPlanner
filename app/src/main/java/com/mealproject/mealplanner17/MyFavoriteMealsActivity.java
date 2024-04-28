package com.mealproject.mealplanner17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mealproject.mealplanner17.API.RecipeDetailsActivity;
import com.mealproject.mealplanner17.API.RequestManager;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.Listeners.RecipeDetailsListener;
import com.mealproject.mealplanner17.ModelsAPI.Recipe;
import com.mealproject.mealplanner17.ModelsAPI.RecipeDetailsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFavoriteMealsActivity extends AppCompatActivity implements RecipeClickListener {

    private RecyclerView recyclerView;
    private RandomRecipeAdapter adapter;
    private List<Recipe> favoriteRecipes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_meals);

        recyclerView = findViewById(R.id.recycler_favorite_meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFavoriteRecipes();
    }

    private void loadFavoriteRecipes() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyFavoriteMeals", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        favoriteRecipes = new ArrayList<>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String id = entry.getKey();
            boolean isFavorite = (Boolean) entry.getValue();

            // Log the entry details
            Log.d("MyFavoriteMeals", "Entry: ID = " + id + ", IsFavorite = " + isFavorite);

            // If the recipe is marked as favorite, add it to the list
            if (isFavorite) {
                // Fetch the recipe details using id and add to favoriteRecipes
                RequestManager manager = new RequestManager(this);
                manager.getRecipeDetails(new RecipeDetailsListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void didFetch(RecipeDetailsResponse response, String message) {
                        // Log the fetched recipe details
                        Log.d("MyFavoriteMeals", "Fetched Recipe: ID = " + response.id + ", Title = " + response.title);

                        favoriteRecipes.add(new Recipe(
                                response.id,
                                response.title,
                                response.image,
                                response.servings,
                                response.sourceName
                        ));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void didError(String message) {
                        Log.e("MyFavoriteMeals", "Error fetching recipe: " + message);
                        Toast.makeText(MyFavoriteMealsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }, Integer.parseInt(id));
            } else {

                Log.d("MyFavoriteMeals", "Recipe with ID = " + id + " is not a favorite");
            }
        }

        // Set up the adapter with the favorite recipes list
        adapter = new RandomRecipeAdapter(this, favoriteRecipes, this);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onRecipeClick(String id) {


        startActivity(new Intent(MyFavoriteMealsActivity.this, RecipeDetailsActivity.class)
                .putExtra("id", id));
    }
}
