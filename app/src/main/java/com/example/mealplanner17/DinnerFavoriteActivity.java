package com.example.mealplanner17;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

public class DinnerFavoriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_favorite);

        // Retrieve EditText reference
        EditText editTextFavorite = findViewById(R.id.editTextFavorite);
//hello
        // You can now use editTextFavorite to get user input or perform any other actions
    }
}
