package com.example.mealplanner17.LunchActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mealplanner17.MyFavoriteMealsActivity;
import com.example.mealplanner17.R; // Add this import statement

public class LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        Button buttonGenerateLunch = findViewById(R.id.buttonGenerateLunch);
        Button buttonAddFavoriteLunch = findViewById(R.id.buttonAddFavoriteLunch);
        Button buttonViewData = findViewById(R.id.buttonViewData); // Reference to the view data button
        Button viewSaveButton= findViewById(R.id.buttonFavoriteRandomLunchDishes);

        buttonGenerateLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LunchActivity.this, LunchGenerateActivity.class);
                startActivity(intent);
            }
        });

        buttonAddFavoriteLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LunchActivity.this, LunchFavoriteActivity.class);
                startActivity(intent);
            }
        });

        buttonViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LunchViewDataActivity to view the data
                Intent intent = new Intent(LunchActivity.this, LunchViewDataActivity.class);
                startActivity(intent);
            }
        });

        viewSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LunchActivity.this, MyFavoriteMealsActivity.class);
                startActivity(intent);
            }
        });
    }
}
