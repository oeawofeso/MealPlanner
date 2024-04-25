package com.example.mealplanner17.Breakfast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mealplanner17.API.RecipeDetailsActivity;
import com.example.mealplanner17.API.RequestManager;
import com.example.mealplanner17.Adapters.RandomRecipeAdapter;
import com.example.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.example.mealplanner17.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BreakfastGenerateActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private RequestManager manager;
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecyclerView recyclerView;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button button;

    // Spinner objects
    private Spinner spinner;
    private Spinner spinnerEx;
    private List<String> breakfastTags = new ArrayList<>();
    private List<String> breakfastTagsExclude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_generate);

        initializeViews();
        setupSpinners();
        setupFirebase();
        setupRecipeManager();
        loadInitialData();
    }

    private void initializeViews() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        spinner = findViewById(R.id.spinner_tags);
        spinnerEx = findViewById(R.id.spinner_tags2);
        recyclerView = findViewById(R.id.recycler_random);
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

    private void setupFirebase() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void setupRecipeManager() {
        manager = new RequestManager(this);
    }

    private void loadInitialData() {
        breakfastTags.add("breakfast");
        breakfastTagsExclude.add("breakfast");
        manager.getRandomRecipes(randomRecipeResponseListener, breakfastTags, breakfastTagsExclude);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(BreakfastGenerateActivity.this, 1));

            randomRecipeAdapter = new RandomRecipeAdapter(BreakfastGenerateActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(BreakfastGenerateActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            updateBreakfastTags(adapterView.getSelectedItem().toString(), true);
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
            updateBreakfastTags(adapterView.getSelectedItem().toString(), false);
            manager.getRandomRecipes(randomRecipeResponseListener, breakfastTags, breakfastTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void updateBreakfastTags(String selectedTag, boolean include) {
        if (include) {
            breakfastTags.clear();
            breakfastTags.add("breakfast");
            if (!selectedTag.equals("breakfast")) {
                breakfastTags.add(selectedTag);
            }
        } else {
            breakfastTagsExclude.clear();
            breakfastTagsExclude.add(selectedTag);
        }
        Log.d("BreakfastGenerateActivity", "tagsInc: " + breakfastTags.toString());
        Log.d("BreakfastGenerateActivity", "tagsExclude: " + breakfastTagsExclude.toString());
    }

    private final RecipeClickListener recipeClickListener = id -> {
        startActivity(new Intent(BreakfastGenerateActivity.this, RecipeDetailsActivity.class)
                .putExtra("id", id));
    };
}
