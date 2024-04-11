package com.example.mealplanner17;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BreakfastFavoriteActivity extends AppCompatActivity {
    private EditText mealNameEditText;
    private EditText ingredientsEditText;
    private EditText cookingInstructionsEditText;
    private Button saveButton;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_favorite);

        // Initialize EditText and Button views
        mealNameEditText = findViewById(R.id.editTextMealName);
        ingredientsEditText = findViewById(R.id.editTextIngredients);
        cookingInstructionsEditText = findViewById(R.id.editTextCookingInstructions);
        saveButton = findViewById(R.id.btnSaveMeal); // Finding the Save button

        // Create or open the database
        SQLiteOpenHelper dbHelper = new RecipeDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Set OnClickListener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields
                String mealName = mealNameEditText.getText().toString();
                String ingredients = ingredientsEditText.getText().toString();
                String cookingInstructions = cookingInstructionsEditText.getText().toString();

                // Add the recipe to the database
                addRecipe(mealName, ingredients, cookingInstructions);

                // Display a toast message indicating successful save
                Toast.makeText(BreakfastFavoriteActivity.this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();

                // Clear EditText fields after saving
                mealNameEditText.setText("");
                ingredientsEditText.setText("");
                cookingInstructionsEditText.setText("");
            }
        });
    }

    // Function to add a new recipe to the database
    private void addRecipe(String mealName, String ingredients, String cookingInstructions) {
        // Insert the new recipe into the 'recipes' table
        ContentValues values = new ContentValues();
        values.put("meal_name", mealName);
        values.put("ingredients", ingredients);
        values.put("cooking_instructions", cookingInstructions);
        db.insert("recipes", null, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when the activity is destroyed
        if (db != null) {
            db.close();
        }
    }
}
