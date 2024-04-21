package com.example.mealplanner17;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner17.Breakfast.BreakfastFavoriteActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                // Start the FavoritesActivity
                Intent intent = new Intent(ProfileActivity.this, BreakfastFavoriteActivity.class);
                startActivity(intent);
            }
        });
    }
}