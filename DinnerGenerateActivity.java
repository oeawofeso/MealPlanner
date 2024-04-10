package com.example.mealplanner17;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner17.API.RecipeDetailsActivity;
import com.example.mealplanner17.API.RequestManager;
import com.example.mealplanner17.Adapters.RandomRecipeAdapter;
import com.example.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.example.mealplanner17.Listeners.RecipeClickListener;
import com.example.mealplanner17.Models.RandomRecipeApiResponse;
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
    Button button;
    FirebaseUser user;

    // Spinner objects
    Spinner spinner;
    Spinner spinnerEx;
    List<String> dinnerTags = new ArrayList<>();
    List<String> dinnerTagsExclude = new ArrayList<>();


    // SearchView
    SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_generate);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.dinnerTags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        spinnerEx = findViewById(R.id.spinner_tags2);
        ArrayAdapter arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.dinnerTagsExclude, R.layout.spinner_text);
        arrayAdapter2.setDropDownViewResource(R.layout.spinner_inner_text);
        spinnerEx.setAdapter(arrayAdapter2);
        spinnerEx.setOnItemSelectedListener(spinnerSelectedListener2);


        manager = new RequestManager(this);
        dinnerTags.clear();
        dinnerTags.add("dinner");

        dinnerTagsExclude.clear();
        dinnerTagsExclude.add("dinner");
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
            dinnerTags.add("dinner");
            dinnerTags.add(adapterView.getSelectedItem().toString());

            dinnerTagsExclude.clear();
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
            // Strip HTML tags from the id string
            String cleanId = stripHtmlTags(id);

            startActivity(new Intent(DinnerGenerateActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", cleanId));
        }
    };

    private String stripHtmlTags(String html) {
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().replaceAll("\\<.*?\\>", "");
    }

}
