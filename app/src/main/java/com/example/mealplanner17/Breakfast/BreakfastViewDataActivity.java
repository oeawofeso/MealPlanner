package com.example.mealplanner17.Breakfast;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner17.R;

public class BreakfastViewDataActivity extends AppCompatActivity {

    BreakfastDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_view_data);

        dbHelper = new BreakfastDBHelper(this);

        displayBreakfastData();
    }

    private void displayBreakfastData() {
        TextView textViewData = findViewById(R.id.textViewData);

        Cursor cursor = dbHelper.getAllBreakfastDataWithIndex(); // Use method with index

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
                    long breakfastEntryId = cursor.getLong(indexId);
                    Log.d("BreakfastViewDataActivity", "_id: " + breakfastEntryId);
                    Log.d("BreakfastViewDataActivity", "Meal Name: " + mealName);
                    Log.d("BreakfastViewDataActivity", "Ingredients: " + ingredients);
                    Log.d("BreakfastViewDataActivity", "Cooking Instructions: " + instructions);

                    stringBuilder.append("Index: ").append(index).append("\n"); // Append index
                    stringBuilder.append("Meal Name: ").append(mealName).append("\n");
                    stringBuilder.append("Ingredients: ").append(ingredients).append("\n");
                    stringBuilder.append("Cooking Instructions: ").append(instructions).append("\n\n");
                    index++; // Increment index
                } else {
                    Log.e("BreakfastViewDataActivity", "Column index not found.");
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("BreakfastViewDataActivity", "Cursor is null or empty.");
            stringBuilder.append("No data available.");
        }

        textViewData.setText(stringBuilder.toString());
    }


    public void onDeleteByIndexButtonClick(View view) {
        EditText editTextIndexToDelete = findViewById(R.id.editTextIndexToDelete);
        String indexStr = editTextIndexToDelete.getText().toString();
        if (!indexStr.isEmpty()) {
            int index = Integer.parseInt(indexStr);
            dbHelper.deleteBreakfastMealByIndex(index);
            displayBreakfastData(); // Refresh displayed data after deletion
        } else {
            Toast.makeText(this, "Please enter an index to delete", Toast.LENGTH_SHORT).show();
        }
    }
}
