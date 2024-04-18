package com.example.mealplanner17.Breakfast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.API.RecipeDetailsActivity;
import com.example.mealplanner17.API.RequestManager;
import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.Adapters.RandomRecipeAdapter;
import com.example.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.example.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.example.mealplanner17.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.ProgressDialog;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BreakfastSidesActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private RequestManager manager;
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecyclerView recyclerView;
    private Spinner spinnerInclude;
    private Spinner spinnerExclude;

    private List<String> drinkTags = new ArrayList<>();
    private List<String> drinkTagsExclude = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_generate);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        // Initialize spinners for include and exclude filters
        spinnerInclude = findViewById(R.id.spinner_tags);
        ArrayAdapter<CharSequence> adapterInclude = ArrayAdapter.createFromResource(
                this, R.array.breakfastDrinkTags, R.layout.spinner_text);
        adapterInclude.setDropDownViewResource(R.layout.spinner_inner_text);
        spinnerInclude.setAdapter(adapterInclude);
        spinnerInclude.setOnItemSelectedListener(spinnerIncludeListener);

        spinnerExclude = findViewById(R.id.spinner_tags2);
        ArrayAdapter<CharSequence> adapterExclude = ArrayAdapter.createFromResource(
                this, R.array.breakfastDrinkTagsExclude, R.layout.spinner_text);
        adapterExclude.setDropDownViewResource(R.layout.spinner_inner_text);
        spinnerExclude.setAdapter(adapterExclude);
        spinnerExclude.setOnItemSelectedListener(spinnerExcludeListener);

        // Initialize the request manager
        manager = new RequestManager(this);

        // Set up initial tags to fetch only drink options
        drinkTags.clear();
        drinkTags.add("drink"); // Adjust this to your desired drink tag

        drinkTagsExclude.clear();

        // Make initial request to fetch drink options
        manager.getRandomRecipes(randomRecipeResponseListener, drinkTags, drinkTagsExclude);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(BreakfastSidesActivity.this, 1));

            randomRecipeAdapter = new RandomRecipeAdapter(BreakfastSidesActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(BreakfastSidesActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerIncludeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            // Clear existing tags and add drink tag
            drinkTags.clear();
            drinkTags.add("drink"); // Adjust this to your desired drink tag

            // Add additional tag from the spinner selection
            String selectedTag = adapterView.getSelectedItem().toString();
            if (!selectedTag.equals("drink")) {
                drinkTags.add(selectedTag);
            }

            // Make an API request with the new filters
            manager.getRandomRecipes(randomRecipeResponseListener, drinkTags, drinkTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // No action needed
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerExcludeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            // Clear existing exclude tags and add the selected exclude tag
            drinkTagsExclude.clear();
            drinkTagsExclude.add(adapterView.getSelectedItem().toString());

            // Make an API request with the new filters
            manager.getRandomRecipes(randomRecipeResponseListener, drinkTags, drinkTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // No action needed
        }
    };

    private final RecipeClickListener recipeClickListener = id -> {
        Intent intent = new Intent(BreakfastSidesActivity.this, RecipeDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    };
    public static void fetchAndDisplayDrinks(Context context, RandomRecipeAdapter adapter, RandomRecipeResponseListener listener) {
        // Define the tags for fetching drinks
        List<String> drinkTags = new ArrayList<>();
        drinkTags.add("drink"); // Add the drink tag

        List<String> drinkTagsExclude = new ArrayList<>(); // Define exclusions if necessary

        // Create a RequestManager instance
        RequestManager manager = new RequestManager(context);

        // Fetch drinks recipes
        manager.getRandomRecipes(new RandomRecipeResponseListener() {
            @Override
            public void didFetch(RandomRecipeApiResponse response, String message) {
                // Update the adapter with the fetched drinks recipes
                adapter.updateData(response.recipes);
                listener.didFetch(response, message);
            }

            @Override
            public void didError(String message) {
                // Handle errors
                listener.didError(message);
            }
        }, drinkTags, drinkTagsExclude);
    }


}
