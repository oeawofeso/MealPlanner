package com.example.mealplanner17;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    FirebaseUser user;

    public String getGreet(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        /**
         * Get the time of day
         */
        String greet = null;
        if(hour>= 12 && hour < 17){
            greet= "Good Afternoon";
        } else if(hour >= 17 && hour < 21){
            greet = "Good Evening";
        } else if(hour >= 21 && hour < 24){
            greet = "Good Night";
        } else {
            greet = "Good Morning";
        }
        return greet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout_button);
        Button breakfastButton = findViewById(R.id.breakfast_button);
        Button lunchButton = findViewById(R.id.lunch_button);
        Button dinnerButton = findViewById(R.id.dinner_button);

        user= auth.getCurrentUser();
        TextView greeting = (TextView) findViewById (R.id.greetingID);
        // Get the SharedPreferences object for "UserDetails"
        SharedPreferences prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

        // Retrieve data from SharedPreferences
        String firstName = prefs.getString("first_name", "defaultFirstName"); // "defaultFirstName" is a default value.
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        String Greet = getGreet() + ", " +firstName;
        greeting.setText(Greet);
        button.setOnClickListener(new View.OnClickListener() { //logout button
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Breakfast button click
                Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
                startActivity(intent);
            }
        });
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });

    }
}