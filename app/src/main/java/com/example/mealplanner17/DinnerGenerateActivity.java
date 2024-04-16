package com.example.mealplanner17;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

public class DinnerGenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_generate);

        // Retrieve EditText reference
        EditText editText = findViewById(R.id.editText);

        // You can now use editText to get user input or perform any other actions
    }
}
