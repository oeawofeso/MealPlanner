package com.mealproject.mealplanner17.LunchActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mealproject.mealplanner17.API.LunchDetailsActivity;
import com.mealproject.mealplanner17.API.RecipeDetailsActivity;
import com.mealproject.mealplanner17.API.RequestManager;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.mealproject.mealplanner17.R;

import java.util.ArrayList;
import java.util.List;

public class LunchSidesActivity extends AppCompatActivity {
    ProgressDialog dialog;
    Spinner spinner;
    Spinner spinner2;

    RandomRecipeAdapter randomRecipeAdapter;

    RecyclerView recyclerView;
    RequestManager manager;
    List<String> sideDishTags = new ArrayList<>(); // Change the list name to reflect side dishes
    List<String> sideDishTagsExclude = new ArrayList<>(); // Change the list name to reflect side dishes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_generate);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        // Initialize spinners and adapters...

        manager = new RequestManager(this);
        sideDishTags.clear();
        sideDishTags.add("side dish");

        sideDishTagsExclude.clear();
        sideDishTagsExclude.add("side dish");
        manager.getRandomRecipes(randomRecipeResponseListener, sideDishTags, sideDishTagsExclude);
        dialog.show();
    }


    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_view_horizontal);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(LunchSidesActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            randomRecipeAdapter = new RandomRecipeAdapter(LunchSidesActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(LunchSidesActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(LunchSidesActivity.this, LunchDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            // Implement spinner selection logic if needed
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener_2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            // Implement spinner selection logic if needed
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
