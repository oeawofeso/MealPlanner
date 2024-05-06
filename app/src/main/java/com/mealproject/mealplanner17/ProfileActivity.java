package com.mealproject.mealplanner17;

import android.content.Intent;
import android.graphics.Bitmap;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * The ProfileActivity class manages user profile settings and display.
 * It allows users to set and update their profile information such as username, profile picture,
 * weight, height, and goal weight.
 *
 * This activity includes functionality to select profile picture from the device's gallery,
 * set and update username, and select weight, height, and goal weight from predefined lists.
 *
 * References:
 * - Modern Profile UI Design in Android: https://awsrh.blogspot.com/2017/10/modern-profile-ui-design-in-android.html
 */
public class ProfileActivity extends AppCompatActivity {
    private Spinner weightSpinner;
    private Spinner goalWeightSpinner;
    private Spinner heightSpinner;
    private TextView emailTextView;
    private TextView profileNameTextView;
    private int selectedWeight;
    private int selectedHeightInInches;
    private EditText usernameEditText;
    private SharedPreferences sharedPreferences;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private ImageView profileImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("ProfilePrefs", MODE_PRIVATE);
        profileImageView = findViewById(R.id.profile_image_view);
        weightSpinner = findViewById(R.id.weight_spinner);
        goalWeightSpinner = findViewById(R.id.goal_weight_spinner);
        heightSpinner = findViewById(R.id.height_spinner);
        emailTextView = findViewById(R.id.email_text_view);
        usernameEditText = findViewById(R.id.username_edit_text);
        profileNameTextView = findViewById(R.id.profile_name_text_view);
        ArrayAdapter<Integer> weightAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                generateWeightValues());
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                generateHeightValues());
        weightSpinner.setAdapter(weightAdapter);
        goalWeightSpinner.setAdapter(weightAdapter);
        heightSpinner.setAdapter(heightAdapter);

        String savedUsername = sharedPreferences.getString("username", "");
        usernameEditText.setText(savedUsername);
        profileNameTextView.setText(savedUsername);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String newName = s.toString();
                profileNameTextView.setText(newName);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newName);
                editor.apply();
            }
        });
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            emailTextView.setText(email);
        } else {
            emailTextView.setText("Not logged in");
        }
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWeight = (int) parent.getItemAtPosition(position);
                // Save selected weight to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedWeight", selectedWeight);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });
        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHeightInInches = position;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedHeight", selectedHeightInInches);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        goalWeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWeight = (int) parent.getItemAtPosition(position);
                // Save selected weight to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedWeight", selectedWeight);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private Integer[] generateWeightValues() {
        Integer[] weights = new Integer[401];
        for (int i = 0; i <= 400; i++) {
            weights[i] = i;
        }
        return weights;
    }
    private String[] generateHeightValues() {
        String[] heights = new String[97]; // 8 feet = 96 inches
        for (int i = 0; i <= 96; i++) {
            int feet = i / 12;
            int inches = i % 12;
            heights[i] = feet + "'" + inches + "''";
        }
        return heights;
    }
    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
