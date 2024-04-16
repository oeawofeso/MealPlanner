package com.example.mealplanner17.Breakfast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mealplanner17.MyFavoriteMealsActivity;
import com.example.mealplanner17.R;

public class BreakfastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button3 = findViewById(R.id.button3);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click - navigate to BreakfastGenerateActivity
                Intent intent = new Intent(BreakfastActivity.this, BreakfastGenerateActivity.class);
                startActivity(intent);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 2 click - navigate to BreakfastFavoriteActivity
                Intent intent = new Intent(BreakfastActivity.this, BreakfastFavoriteActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BreakfastActivity.this, MyFavoriteMealsActivity.class);
                startActivity(intent);
            }
        });

    }
}
