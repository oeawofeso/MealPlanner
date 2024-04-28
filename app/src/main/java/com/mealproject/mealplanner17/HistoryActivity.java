package com.mealproject.mealplanner17;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mealproject.mealplanner17.API.RecipeDetailsActivity;
import com.mealproject.mealplanner17.API.RequestManager;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.Listeners.RecipeDetailsListener;
import com.mealproject.mealplanner17.ModelsAPI.Recipe;
import com.mealproject.mealplanner17.ModelsAPI.RecipeDetailsResponse;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity implements RecipeDetailsListener, RecipeClickListener {

    private RecyclerView recyclerView;
    private TextView noHistoryText;
    private RandomRecipeAdapter historyAdapter;
    private List<Recipe> historyRecipes;
    private Set<String> clickedRecipeIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize UI elements
        recyclerView = findViewById(R.id.history_recycler_view);




        // Retrieve clicked recipe IDs from Singleton
        clickedRecipeIds = ClickedRecipeIdsSingleton.getInstance().getClickedRecipeIds();

        // Initialize empty list for history recipes
        historyRecipes = new ArrayList<>();

        // Check if clicked recipes exist
        if (!clickedRecipeIds.isEmpty()) {
            fetchRecipeDetails(clickedRecipeIds);
        } else {
            // No clicked recipes
            noHistoryText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void fetchRecipeDetails(Set<String> recipeIds) {
        RequestManager manager = new RequestManager(this);
        for (String id : recipeIds) {
            manager.getRecipeDetails(this, Integer.parseInt(id));
        }
    }

    @Override
    public void didFetch(RecipeDetailsResponse response, String message) {
        Log.d("HistoryActivity", "Fetched Recipe: ID = " + response.id + ", Title = " + response.title);

        historyRecipes.add(new Recipe(
                response.id,
                response.title,
                response.image,
                response.servings,
                response.sourceName
        ));

        // Update adapter if all recipes are fetched
        if (historyRecipes.size() == clickedRecipeIds.size()) {
            updateRecyclerView();
        }
    }

    @Override
    public void didError(String message) {
        Log.e("HistoryActivity", "Error fetching recipe details: " + message);
        Toast.makeText(HistoryActivity.this, "Error fetching history recipes", Toast.LENGTH_SHORT).show();
    }

    private void updateRecyclerView() {
        historyAdapter = new RandomRecipeAdapter(this, historyRecipes, this );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onRecipeClick(String id) {
        startActivity(new Intent(HistoryActivity.this, RecipeDetailsActivity.class)
                .putExtra("id", id));
    }
}
