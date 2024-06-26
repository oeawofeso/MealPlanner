package com.mealproject.mealplanner17;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DinnerFavoriteActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_favorite);

        // Instantiate DBHelper
        dbHelper = new DBHelper(this);

        Button saveButton = findViewById(R.id.btnSaveMeal);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMealData();
            }
        });
    }

    private void saveMealData() {
        EditText editTextMealName = findViewById(R.id.editTextMealName);
        EditText editTextIngredients = findViewById(R.id.editTextIngredients);
        EditText editTextCookingInstructions = findViewById(R.id.editTextCookingInstructions);

        String mealName = editTextMealName.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String cookingInstructions = editTextCookingInstructions.getText().toString().trim();

        // Check if any field is empty
        if (mealName.isEmpty() || ingredients.isEmpty() || cookingInstructions.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call insertDinner method of DBHelper
        dbHelper.insertDinner(mealName, ingredients, cookingInstructions);

        // Show a success message
        Toast.makeText(this, "Meal data saved successfully", Toast.LENGTH_SHORT).show();

        // Optionally, you can clear the input fields here
        editTextMealName.setText("");
        editTextIngredients.setText("");
        editTextCookingInstructions.setText("");
    }
}
