package com.example.mealplanner17;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DinnerViewDataActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_view_data);

        dbHelper = new DBHelper(this);

        displayDinnerData();

        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the ID of the dinner entry you want to delete
                // Here, you need to implement a way to get the ID. For example, if the ID is stored as a tag in the delete button itself:
                long id = (long) v.getTag();

                // Call deleteDinnerData method with the retrieved ID
                deleteDinnerData(id);
            }
        });
    }

    private void displayDinnerData() {
        TextView textViewData = findViewById(R.id.textViewData);

        Cursor cursor = dbHelper.getAllDinnerData();

        StringBuilder stringBuilder = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int indexMealName = cursor.getColumnIndex("food_name");
                int indexIngredients = cursor.getColumnIndex("ingredients");
                int indexInstructions = cursor.getColumnIndex("cooking_instructions");
                int indexId = cursor.getColumnIndex("_id"); // Added index for _id column

                if (indexMealName != -1 && indexIngredients != -1 && indexInstructions != -1) {
                    String mealName = cursor.getString(indexMealName);
                    String ingredients = cursor.getString(indexIngredients);
                    String instructions = cursor.getString(indexInstructions);
                    long dinnerEntryId = -1; // Default value if column not found or value is null
                    if (indexId != -1) {
                        dinnerEntryId = cursor.getLong(indexId);
                    } else {
                        Log.e("DinnerViewDataActivity", "Column '_id' not found in cursor.");
                    }

                    Log.d("DinnerViewDataActivity", "Meal Name: " + mealName);
                    Log.d("DinnerViewDataActivity", "Ingredients: " + ingredients);
                    Log.d("DinnerViewDataActivity", "Cooking Instructions: " + instructions);

                    stringBuilder.append("Meal Name: ").append(mealName).append("\n");
                    stringBuilder.append("Ingredients: ").append(ingredients).append("\n");
                    stringBuilder.append("Cooking Instructions: ").append(instructions).append("\n\n");

                    Button deleteButton = findViewById(R.id.buttonDelete);
                    deleteButton.setTag(dinnerEntryId);
                } else {
                    Log.e("DinnerViewDataActivity", "Column index not found.");
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("DinnerViewDataActivity", "Cursor is null or empty.");
            stringBuilder.append("No data available.");
        }

        textViewData.setText(stringBuilder.toString());
    }




    // Method to delete dinner data
    private void deleteDinnerData(long id) {
        dbHelper.deleteDinner(id);
        // Refresh the displayed data after deletion
        displayDinnerData();
    }
}
