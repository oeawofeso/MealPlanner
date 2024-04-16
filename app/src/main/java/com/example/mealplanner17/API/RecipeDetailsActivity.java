package com.example.mealplanner17.API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner17.Adapters.IngredientsAdapter;

import com.example.mealplanner17.Adapters.InstructionAdapter;
import com.example.mealplanner17.Adapters.SideDishesAdapter;
import com.example.mealplanner17.Listeners.RecipeDetailsListener;
import com.example.mealplanner17.ModelsAPI.InstructionResponse;
import com.example.mealplanner17.ModelsAPI.RecipeDetailsResponse;
import com.example.mealplanner17.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name,textView_meal_source;
    ImageView imageView_meal_image,imageView_ingredients;
    RecyclerView recycler_meal_ingredients,recipeStepsRecylerView;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    Button btnFavorite;




    private static final String TAG = "RecipeDetailsActivity";
    private final static String API_KEY = "3ef8f95c17944ed6bac930281b8b453e";
    ApiCallForRecpiesInterface APIgetRecipes;
    List<InstructionResponse> recipeSteps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        APIgetRecipes = ApiCallForRecpies.getClient().create(ApiCallForRecpiesInterface.class);

        initObjects();

        id=  Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));
        manager= new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener,id);

        recipeStepsRecylerView = findViewById(R.id.stepList);


        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();

        Button buttonGenerateSideDishes = findViewById(R.id.button_generate_side_dishes);
        buttonGenerateSideDishes.setOnClickListener(v -> generateSideDishes());







    }


    private void initObjects() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);

        imageView_meal_image= findViewById(R.id.imageView_meal_image);
        imageView_ingredients = findViewById(R.id.imageView_ingredients);
        recycler_meal_ingredients= findViewById(R.id.recycler_meal_ingredients);

    }
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);


            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));

            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

            APIgetRecipes = ApiCallForRecpies.getClient().create(ApiCallForRecpiesInterface.class);
            getInstructionsForRecipe(id);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private void getInstructionsForRecipe(int id) {
        Call<List<InstructionResponse>> call = APIgetRecipes.getInstructions(id,API_KEY);
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call <List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {

                assert response.body() != null;
                recipeSteps = response.body();

                for (int i = 0; i< recipeSteps.get(0).getSteps().size(); i++){
                    Log.d(TAG, "Step: Number "+ recipeSteps.get(0).getSteps().get(i).getNumber()+" " + recipeSteps.get(0).getSteps().get(i).getStep());
                }
                recipeStepsRecylerView.setLayoutManager(new LinearLayoutManager( getApplicationContext()));
                recipeStepsRecylerView.setAdapter(new InstructionAdapter(recipeSteps.get(0).getSteps(), R.layout.eachsteps, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }
    private void generateSideDishes() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Side Dishes...");
        dialog.show();

        ApiCallForRecpiesInterface APIgetRecipes = ApiCallForRecpies.getClient().create(ApiCallForRecpiesInterface.class);
        Call<List<RecipeDetailsResponse>> call = APIgetRecipes.getRandomRecipes("side dish", API_KEY, 3);  // Fetch 3 random side dishes

        call.enqueue(new Callback<List<RecipeDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeDetailsResponse>> call, Response<List<RecipeDetailsResponse>> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    List<RecipeDetailsResponse> sideDishes = response.body();
                    // Call a method to display the side dishes, e.g. updateSideDishesUI(sideDishes);
                } else {
                    Toast.makeText(RecipeDetailsActivity.this, "Failed to load side dishes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeDetailsResponse>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(RecipeDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateSideDishesUI(List<RecipeDetailsResponse> sideDishes) {

        RecyclerView sideDishesRecyclerView = findViewById(R.id.side_dishes_recycler_view);
        SideDishesAdapter adapter = new SideDishesAdapter(this, sideDishes);
        sideDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sideDishesRecyclerView.setAdapter(adapter);
    }

}