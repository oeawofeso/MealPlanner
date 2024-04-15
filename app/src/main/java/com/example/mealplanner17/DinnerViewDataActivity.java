package com.example.mealplanner17;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DinnerViewDataActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_view_data);

        // Initialize DBHelper instance
        dbHelper = new DBHelper(this);

        // Call method to display dinner data
        displayDinnerData();
    }

    private void displayDinnerData() {
        TextView textViewData = findViewById(R.id.textViewData);

        // Retrieve data from the database
        Cursor cursor = dbHelper.getAllDinnerData();

        // Append the data to the TextView
        StringBuilder stringBuilder = new StringBuilder();
        // Before accessing cursor methods, ensure cursor is not null
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Check if cursor has columns
                if (cursor.getColumnCount() > 0) {
                    // Retrieve column indices
                    int indexMealName = cursor.getColumnIndex("food_name");
                    int indexIngredients = cursor.getColumnIndex("ingredients");
                    int indexInstructions = cursor.getColumnIndex("cooking_instructions");

                    // Check if column indices are valid
                    if (indexMealName != -1 && indexIngredients != -1 && indexInstructions != -1) {
                        // Retrieve data using column indices
                        String mealName = cursor.getString(indexMealName);
                        String ingredients = cursor.getString(indexIngredients);
                        String instructions = cursor.getString(indexInstructions);

                        // Log the retrieved data for debugging
                        Log.d("DinnerViewDataActivity", "Meal Name: " + mealName);
                        Log.d("DinnerViewDataActivity", "Ingredients: " + ingredients);
                        Log.d("DinnerViewDataActivity", "Cooking Instructions: " + instructions);

                        // Append the data to the TextView
                        stringBuilder.append("Meal Name: ").append(mealName).append("\n");
                        stringBuilder.append("Ingredients: ").append(ingredients).append("\n");
                        stringBuilder.append("Cooking Instructions: ").append(instructions).append("\n\n");
                    } else {
                        Log.e("DinnerViewDataActivity", "Column index not found.");
                    }
                } else {
                    Log.e("DinnerViewDataActivity", "No columns found in the cursor.");
                }
            } while (cursor.moveToNext());
            cursor.close(); // Close the cursor after data retrieval
        } else {
            Log.e("DinnerViewDataActivity", "Cursor is null or empty.");
            stringBuilder.append("No data available.");
        }

        // Display the data in the TextView
        textViewData.setText(stringBuilder.toString());
    }

    // Method to delete dinner data
    private void deleteDinnerData(long id) {
        dbHelper.deleteDinner(id);
        // Refresh the displayed data after deletion
        displayDinnerData();
    }
}
