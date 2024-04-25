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

import com.mealproject.mealplanner17.API.RecipeDetailsActivity;
import com.mealproject.mealplanner17.API.RequestManager;
import com.mealproject.mealplanner17.Adapters.RandomRecipeAdapter;
import com.mealproject.mealplanner17.Listeners.RandomRecipeResponseListener;
import com.mealproject.mealplanner17.Listeners.RecipeClickListener;
import com.mealproject.mealplanner17.ModelsAPI.RandomRecipeApiResponse;
import com.mealproject.mealplanner17.R;

import java.util.ArrayList;
import java.util.List;

public class LunchGenerateActivity extends AppCompatActivity {
    ProgressDialog dialog;
    Spinner spinner;
    Spinner spinner2;

    RandomRecipeAdapter randomRecipeAdapter;

    Button EnterButt;
    RecyclerView recyclerView;
    RequestManager manager;
    List<String> lunchTags= new ArrayList<>();
    List<String> lunchTagsExclude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_generate);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");

        spinner = findViewById(R.id.filter_spinner);
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this, R.array.foodTypes, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        spinner2 = findViewById(R.id.allergen_spinner);
        ArrayAdapter arrayAdapter2= ArrayAdapter.createFromResource(this, R.array.allergens, R.layout.spinner_text);
        arrayAdapter2.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(spinnerSelectedListener_2);
//        EnterButt = findViewById(R.id.EnterButt);
//        EnterButt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                collectAndFetchRecipes();
//            }
//        });

        manager=new RequestManager(this);
        lunchTags.clear();
        lunchTags.add("lunch");

        lunchTagsExclude.clear();
        lunchTagsExclude.add("lunch");
        manager.getRandomRecipes(randomRecipeResponseListener,lunchTags,lunchTagsExclude);
        dialog.show();
    }




    private final RandomRecipeResponseListener randomRecipeResponseListener=new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_view_horizontal);
            recyclerView.setHasFixedSize(true);

            // Set the LayoutManager to a horizontal LinearLayoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(LunchGenerateActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            // Set the adapter with your data
            randomRecipeAdapter = new RandomRecipeAdapter(LunchGenerateActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(LunchGenerateActivity.this, "message", Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(LunchGenerateActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id",id));
        }
    };


        private final AdapterView.OnItemSelectedListener spinnerSelectedListener= new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            lunchTags.clear();
            lunchTagsExclude.clear();
            lunchTags.add("lunch");
            String selectedTag = adapterView.getSelectedItem().toString();
            if (!selectedTag.equals("lunch")) {
                lunchTags.add(selectedTag);
            }

            Log.d("BreakfastGenerateActivity", "First tagsInc: " + lunchTags.toString());
            Log.d("BreakfastGenerateActivity", "First tagsExclude: " + lunchTagsExclude.toString());

            manager.getRandomRecipes(randomRecipeResponseListener,lunchTags,lunchTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener_2= new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            lunchTags.clear();
            lunchTags.add("breakfast");

            lunchTagsExclude.clear();
            lunchTagsExclude.add(adapterView.getSelectedItem().toString());

            Log.d("BreakfastGenerateActivity", "Second tagsInc: " + lunchTags.toString());
            Log.d("BreakfastGenerateActivity", "Second tagsExclude: " + lunchTagsExclude.toString());

            manager.getRandomRecipes(randomRecipeResponseListener,lunchTags,lunchTagsExclude);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}


//    private void collectAndFetchRecipes() {
//        lunchTags.clear();
//        lunchTagsExclude.clear();
//
//        String foodSelection = spinner.getSelectedItem().toString();
//        String allergenSelection = spinner2.getSelectedItem().toString();
//        lunchTags.add("lunch");
//        // Add the selected items to the tags lists
//        if (!foodSelection.equals("lunch")) {  // Assuming 'lunch' is a default or non-selectable value
//            lunchTags.add(foodSelection);
//        }
//        if (!allergenSelection.isEmpty()) {  // Check for non-empty selection if necessary
//            lunchTagsExclude.add(allergenSelection);
//        }
//
//        String userMess = "Food: " + foodSelection + ", Allergen: " + allergenSelection;
//        Log.d("LunchGenerateActivity", "Second tagsInc: " +  lunchTags.toString());
//        Log.d("LunchGenerateActivity", "Second tagsExclude: " + lunchTagsExclude.toString());
//        Toast.makeText(LunchGenerateActivity.this, "Selected: " + userMess, Toast.LENGTH_LONG).show();
//        System.out.println("Before");
//        // Fetch recipes based on the selected tags
//        lunchTagsExclude.clear();
//        manager.getRandomRecipes(randomRecipeResponseListener, lunchTags, lunchTagsExclude);
//        System.out.println("After");
//        dialog.show();
//
//    }