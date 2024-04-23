package com.example.mealplanner17;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DinnerGenerateActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseUser user;

    // Spinner objects
    Spinner spinnerTags;
    Spinner spinnerTags2;
    List<String> dinnerTags = new ArrayList<>();
    List<String> dinnerTagsExclude = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_generate);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        // Initialize Spinner and Adapter
        spinnerTags = findViewById(R.id.spinner_tags);
        spinnerTags2 = findViewById(R.id.spinner_tags2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dinnerTags, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinnerTags.setAdapter(adapter);
        spinnerTags2.setAdapter(adapter);

        // Initialize RequestManager
        manager = new RequestManager(this);

        // Set up Spinner listeners
        spinnerTags.setOnItemSelectedListener(spinnerSelectedListener);
        spinnerTags2.setOnItemSelectedListener(spinnerSelectedListener2);

        // Initialize lists and add initial values
        dinnerTags.clear();
        dinnerTags.add("dinner");

        dinnerTagsExclude.clear();
        dinnerTagsExclude.add("dinner");

        // Fetch initial data
        manager.getRandomRecipes(randomRecipeResponseListener, dinnerTags, dinnerTagsExclude);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(DinnerGenerateActivity.this, 1));

            randomRecipeAdapter = new RandomRecipeAdapter(DinnerGenerateActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(DinnerGenerateActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            dinnerTags.clear();
            dinnerTagsExclude.clear();
            dinnerTags.add("dinner");
            String selectedTag = adapterView.getSelectedItem().toString();
            if (!selectedTag.equals("dinner")) {
                dinnerTags.add(selectedTag);
            }

            Log.d("DinnerGenerateActivity", "First tagsInc: " + dinnerTags.toString());
            Log.d("DinnerGenerateActivity", "First tagsExclude: " + dinnerTagsExclude.toString());

            manager.getRandomRecipes(randomRecipeResponseListener, dinnerTags, dinnerTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            dinnerTags.clear();
            dinnerTags.add("dinner");

            dinnerTagsExclude.clear();
            dinnerTagsExclude.add(adapterView.getSelectedItem().toString());

            Log.d("DinnerGenerateActivity", "Second tagsInc: " + dinnerTags.toString());
            Log.d("DinnerGenerateActivity", "Second tagsExclude: " + dinnerTagsExclude.toString());

            manager.getRandomRecipes(randomRecipeResponseListener, dinnerTags, dinnerTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(DinnerGenerateActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}
