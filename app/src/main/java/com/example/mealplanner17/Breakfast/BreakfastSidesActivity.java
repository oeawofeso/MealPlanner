package com.example.mealplanner17.Breakfast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mealplanner17.API.RecipeDetailsActivity;
import com.example.mealplanner17.API.RequestManager;
import com.example.mealplanner17.Adapters.RandomRecipeAdapter;
import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.example.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.example.mealplanner17.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BreakfastSidesActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseUser user;


    Spinner spinner;
    List<String> breakfastDrinkTags = new ArrayList<>();
    List<String> breakfastTags = new ArrayList<>();
    List<String> breakfastTagsExclude = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_generate);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.breakfastDrinkTags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);

        breakfastDrinkTags.clear();
        breakfastDrinkTags.add("drink");

        manager.getRandomRecipes(randomRecipeResponseListener, breakfastDrinkTags, new ArrayList<>());
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
            Toast.makeText(BreakfastSidesActivity.this, "message", Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            breakfastDrinkTags.clear();
            breakfastDrinkTags.add("drink");
            String selectedTag = adapterView.getSelectedItem().toString();
            if (!selectedTag.equals("drink")) {
                breakfastDrinkTags.add(selectedTag);
            }

            Log.d("BreakfastSidesActivity", "Selected tags: " + breakfastDrinkTags.toString());

            manager.getRandomRecipes(randomRecipeResponseListener, breakfastDrinkTags, new ArrayList<>());
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(BreakfastSidesActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

    public static void fetchAndDisplayDrinks(Context context, RandomRecipeAdapter adapter, RandomRecipeResponseListener listener) {

        List<String> drinkTags = new ArrayList<>();
        drinkTags.add("drink");

        List<String> drinkTagsExclude = new ArrayList<>();
        RequestManager manager = new RequestManager(context);


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
