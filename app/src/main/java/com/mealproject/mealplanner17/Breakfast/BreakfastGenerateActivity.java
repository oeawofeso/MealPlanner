package com.mealproject.mealplanner17.Breakfast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mealproject.mealplanner17.API.RecipeDetailsActivity;
import com.mealproject.mealplanner17.API.RequestManager;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.mealproject.mealplanner17.ModelsAPI.Recipe;
import com.mealproject.mealplanner17.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The BreakfastGenerateActivity class provides functionalities for generating random breakfast recipes
 * based on selected tags and excluding certain tags. It fetches and displays recipes from the API

 * A RequestManager instance to handle API requests for fetching recipes
 * RandomRecipeResponseListener to handle API response for fetching recipes
 * RecipeClickListener to handle clicks on the displayed recipes, navigating to RecipeDetailsActivity
 * Used API documentation as guide to set up views based on data given
 * https://spoonacular.com/food-api/docs#Get-Random-Recipes
 */
public class BreakfastGenerateActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private RequestManager manager;
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecyclerView recyclerView;
    private Button button;
    private Spinner spinner;
    private Spinner spinnerEx;
    private List<String> breakfastTags = new ArrayList<>();
    private List<String> breakfastTagsExclude = new ArrayList<>();
    private Set<String> clickedRecipeIds = new HashSet<>();
    private SharedPreferences sharedPreferences;
    private static final String PREF_LAST_CLEAR_TIMESTAMP = "last_clear_timestamp";
    private static final long CLEAR_INTERVAL = 7 * 24 * 60 * 60 * 1000; // 7 days in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_generate);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Check if it's time to clear the list
        checkClearClickedRecipeIds();

        initializeViews();
        setupSpinners();
        setupRequestManager();

        randomRecipeAdapter = new RandomRecipeAdapter(BreakfastGenerateActivity.this, new ArrayList<>(), recipeClickListener);
        recyclerView.setAdapter(randomRecipeAdapter);
    }

    private void checkClearClickedRecipeIds() {
        long lastClearTimestamp = sharedPreferences.getLong(PREF_LAST_CLEAR_TIMESTAMP, 0);
        long currentTimestamp = System.currentTimeMillis();

        // Check if 7 days have passed since the last clear
        if (currentTimestamp - lastClearTimestamp >= CLEAR_INTERVAL) {
            clickedRecipeIds.clear(); // Clear the list
            sharedPreferences.edit().putLong(PREF_LAST_CLEAR_TIMESTAMP, currentTimestamp).apply(); // Save current timestamp as last clear timestamp
        }
    }

    private void initializeViews() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        spinner = findViewById(R.id.spinner_tags);
        spinnerEx = findViewById(R.id.spinner_tags2);
        recyclerView = findViewById(R.id.recycler_random);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private void setupSpinners() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_text, getResources().getStringArray(R.array.breakfastTags));
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_text, getResources().getStringArray(R.array.breakfastTagsExclude));
        arrayAdapter2.setDropDownViewResource(R.layout.spinner_inner_text);
        spinnerEx.setAdapter(arrayAdapter2);
        spinnerEx.setOnItemSelectedListener(spinnerSelectedListener2);
    }

    private void setupRequestManager() {
        manager = new RequestManager(this);
        breakfastTags.add("breakfast");
        breakfastTagsExclude.add("breakfast");
        manager.getRandomRecipes(randomRecipeResponseListener, breakfastTags, breakfastTagsExclude);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();

            // Filter out the recipes that have been clicked
            List<Recipe> filteredRecipes = response.recipes.stream()
                    .filter(recipe -> !clickedRecipeIds.contains(recipe.id))
                    .collect(Collectors.toList());

            randomRecipeAdapter.setRecipes(filteredRecipes);
            randomRecipeAdapter.notifyDataSetChanged();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(BreakfastGenerateActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            updateTagsList(breakfastTags, adapterView.getSelectedItem().toString());
            Log.d("BreakfastGenerateActivity", "First tagsInc: " + breakfastTags.toString());
            Log.d("BreakfastGenerateActivity", "First tagsExclude: " + breakfastTagsExclude.toString());
            manager.getRandomRecipes(randomRecipeResponseListener, breakfastTags, breakfastTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            updateTagsList(breakfastTagsExclude, adapterView.getSelectedItem().toString());
            Log.d("BreakfastGenerateActivity", "Second tagsInc: " + breakfastTags.toString());
            Log.d("BreakfastGenerateActivity", "Second tagsExclude: " + breakfastTagsExclude.toString());
            manager.getRandomRecipes(randomRecipeResponseListener, breakfastTags, breakfastTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void updateTagsList(List<String> tagsList, String selectedTag) {
        tagsList.clear();
        tagsList.add("breakfast");
        if (!selectedTag.equals("breakfast")) {
            tagsList.add(selectedTag);
        }
    }

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            clickedRecipeIds.add(id);
            for (Recipe recipe : randomRecipeAdapter.getList()) {
                if (String.valueOf(recipe.id).equals(id)) {
                    String title = recipe.title;
                    Log.d("BreakfastGenerateActivity", "Added clicked recipe - ID: " + id + ", Title: " + title);
                    break;
                }
            }
            startActivity(new Intent(BreakfastGenerateActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}