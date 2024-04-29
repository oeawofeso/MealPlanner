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

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("ProfilePrefs", MODE_PRIVATE);

        profileImageView = findViewById(R.id.profile_image_view);

        // Initialize spinners
        weightSpinner = findViewById(R.id.weight_spinner);
        goalWeightSpinner = findViewById(R.id.goal_weight_spinner);
        heightSpinner = findViewById(R.id.height_spinner);

        emailTextView = findViewById(R.id.email_text_view);
        usernameEditText = findViewById(R.id.username_edit_text);
        profileNameTextView = findViewById(R.id.profile_name_text_view); // Initialize profileNameTextView

        // Create adapters for spinners
        ArrayAdapter<Integer> weightAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                generateWeightValues());

        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                generateHeightValues());

        // Set adapters for spinners
        weightSpinner.setAdapter(weightAdapter);
        goalWeightSpinner.setAdapter(weightAdapter);
        heightSpinner.setAdapter(heightAdapter);

        String savedUsername = sharedPreferences.getString("username", "");
        usernameEditText.setText(savedUsername);

        // Set initial profile name to match the username
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
                // Update the profile name TextView when the text changes
                String newName = s.toString();
                profileNameTextView.setText(newName);

                // Save the updated username to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", newName);
                editor.apply();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is signed in, set email to TextView
            String email = currentUser.getEmail();
            emailTextView.setText(email);
        } else {
            // User is not signed in, handle accordingly
            emailTextView.setText("Not logged in");
        }

        // Handle spinner selections
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
                // Save selected height to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedHeight", selectedHeightInInches);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
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
                // Handle case when nothing is selected
            }
        });
    }

    // Helper method to generate weight values (0 to 400)
    private Integer[] generateWeightValues() {
        Integer[] weights = new Integer[401];
        for (int i = 0; i <= 400; i++) {
            weights[i] = i;
        }
        return weights;
    }

    // Helper method to generate height values (0 to 8 feet)
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
            // Get the Uri of selected image
            Uri imageUri = data.getData();
            try {
                // Set the selected image to the profile image view
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
