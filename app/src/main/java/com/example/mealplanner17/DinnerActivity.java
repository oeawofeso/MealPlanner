package com.example.mealplanner17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        Button button1 = findViewById(R.id.buttonGenerateDinner); // Corrected button id
        Button button2 = findViewById(R.id.buttonAddFavoriteDinner); // Corrected button id

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinnerActivity.this, DinnerGenerateActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinnerActivity.this, DinnerFavoriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
