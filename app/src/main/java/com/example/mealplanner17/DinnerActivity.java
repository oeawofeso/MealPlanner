package com.example.mealplanner17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class DinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        Button buttonGenerateDinner = findViewById(R.id.buttonGenerateDinner);
        Button buttonAddFavoriteDinner = findViewById(R.id.buttonAddFavoriteDinner);
        Button buttonViewData = findViewById(R.id.buttonViewData); // Reference to the view data button

        buttonGenerateDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinnerActivity.this, DinnerGenerateActivity.class);
                startActivity(intent);
            }
        });

        buttonAddFavoriteDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinnerActivity.this, DinnerFavoriteActivity.class);
                startActivity(intent);
            }
        });

        buttonViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Start DinnerViewDataActivity to view the data
                    Intent intent = new Intent(DinnerActivity.this, DinnerViewDataActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception, such as showing an error message to the user
                    Toast.makeText(DinnerActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
