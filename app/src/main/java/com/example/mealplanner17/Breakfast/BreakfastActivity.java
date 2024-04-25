package com.example.mealplanner17.Breakfast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mealplanner17.MyFavoriteMealsActivity;
import com.example.mealplanner17.R;

public class BreakfastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        Button buttonGenerateBreakfast = findViewById(R.id.buttonGenerateBreakfast);
        Button buttonAddFavoriteBreakfast = findViewById(R.id.buttonAddFavoriteBreakfast);
        Button buttonViewBreakfastData = findViewById(R.id.buttonViewBreakfastData);
        Button buttonFavoriteRandomBreakfastDishes = findViewById(R.id.buttonFavoriteRandomBreakfastDishes);

        buttonGenerateBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastActivity.this, BreakfastGenerateActivity.class);
                startActivity(intent);
            }
        });

        buttonAddFavoriteBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastActivity.this, BreakfastFavoriteActivity.class);
                startActivity(intent);
            }
        });

        buttonViewBreakfastData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastActivity.this, BreakfastViewDataActivity.class);
                startActivity(intent);
            }
        });

        buttonFavoriteRandomBreakfastDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakfastActivity.this, MyFavoriteMealsActivity.class);
                startActivity(intent);
            }
        });
    }
}
