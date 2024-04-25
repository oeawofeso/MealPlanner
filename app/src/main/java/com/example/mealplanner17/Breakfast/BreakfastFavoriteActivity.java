package com.example.mealplanner17.Breakfast;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner17.R;

public class BreakfastFavoriteActivity extends AppCompatActivity {

    private BreakfastDBHelper breakfastDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_favorite);

        // Instantiate BreakfastDBHelper
        breakfastDBHelper = new BreakfastDBHelper(this);

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


        if (mealName.isEmpty() || ingredients.isEmpty() || cookingInstructions.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        breakfastDBHelper.insertBreakfastData(mealName, ingredients, cookingInstructions);


        Toast.makeText(this, "Meal data saved successfully", Toast.LENGTH_SHORT).show();


        editTextMealName.setText("");
        editTextIngredients.setText("");
        editTextCookingInstructions.setText("");
    }
}
