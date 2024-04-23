package com.example.mealplanner17.LunchActivities;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mealplanner17.R;

import androidx.appcompat.app.AppCompatActivity;

public class LunchViewDataActivity extends AppCompatActivity {

    LunchDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_view_data);

        dbHelper = new LunchDBHelper(this);

        displayLunchData();
    }

    private void displayLunchData() {
        TextView textViewData = findViewById(R.id.textViewData);

        Cursor cursor = dbHelper.getAllLunchDataWithIndex(); // Use method with index

        StringBuilder stringBuilder = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            int index = 1; // Start index from 1
            do {
                int indexMealName = cursor.getColumnIndex("meal_name");
                int indexIngredients = cursor.getColumnIndex("ingredients");
                int indexInstructions = cursor.getColumnIndex("cooking_instructions");
                int indexId = cursor.getColumnIndex("_id"); // Added index for _id column

                if (indexMealName != -1 && indexIngredients != -1 && indexInstructions != -1) {
                    String mealName = cursor.getString(indexMealName);
                    String ingredients = cursor.getString(indexIngredients);
                    String instructions = cursor.getString(indexInstructions);
                    long lunchEntryId = cursor.getLong(indexId);
                    Log.d("LunchViewDataActivity", "_id: " + lunchEntryId);
                    Log.d("LunchViewDataActivity", "Meal Name: " + mealName);
                    Log.d("LunchViewDataActivity", "Ingredients: " + ingredients);
                    Log.d("LunchViewDataActivity", "Cooking Instructions: " + instructions);

                    stringBuilder.append("Index: ").append(index).append("\n"); // Append index
                    stringBuilder.append("Meal Name: ").append(mealName).append("\n");
                    stringBuilder.append("Ingredients: ").append(ingredients).append("\n");
                    stringBuilder.append("Cooking Instructions: ").append(instructions).append("\n\n");
                    index++; // Increment index
                } else {
                    Log.e("LunchViewDataActivity", "Column index not found.");
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("LunchViewDataActivity", "Cursor is null or empty.");
            stringBuilder.append("No data available.");
        }

        textViewData.setText(stringBuilder.toString());
    }

    // Method to handle delete by index button click
    public void onDeleteByIndexButtonClick(View view) {
        EditText editTextIndexToDelete = findViewById(R.id.editTextIndexToDelete);
        String indexStr = editTextIndexToDelete.getText().toString();
        if (!indexStr.isEmpty()) {
            int index = Integer.parseInt(indexStr);
            dbHelper.deleteLunchMealByIndex(index);
            displayLunchData(); // Refresh displayed data after deletion
        } else {
            Toast.makeText(this, "Please enter an index to delete", Toast.LENGTH_SHORT).show();
        }
    }
}
