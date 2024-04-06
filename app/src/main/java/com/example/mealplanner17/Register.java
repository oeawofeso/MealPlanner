package com.example.mealplanner17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null). If the user is already login in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth= FirebaseAuth.getInstance();
       editTextEmail= findViewById(R.id.Email);
        editTextPassword= findViewById(R.id.Password);
        editTextFirstName = findViewById(R.id.first_name);
        editTextLastName = findViewById(R.id.last_name);
       buttonReg= findViewById(R.id.Register_button);
        progressBar= findViewById(R.id.progressBar);
        textView= findViewById(R.id.LoginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

       buttonReg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressBar.setVisibility(View.VISIBLE);
               String email, password, firstname, lastname;
               email= String.valueOf(editTextEmail.getText());
               password= String.valueOf(editTextPassword.getText());
               firstname = String.valueOf(editTextFirstName.getText());
               lastname = String.valueOf(editTextLastName.getText());
               if(TextUtils.isEmpty(firstname)){
                   Toast.makeText(Register.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                   return;

               }
               if(TextUtils.isEmpty(lastname)){
                   Toast.makeText(Register.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                   return;

               }
               if(TextUtils.isEmpty(email)){
                   Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty(password)){
                   Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                   return;

               }
               System.out.println("Success1...");
               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressBar.setVisibility(View.GONE);
                               if (task.isSuccessful()) {
                                   System.out.println("Success...");
                                   // Sign in success, update UI with the signed-in user's information
                                   Toast.makeText(Register.this, "Account Created.",
                                           Toast.LENGTH_SHORT).show();
                                   /* Store First and Last Name in SharedPreferences (local storage) */
                                   SharedPreferences prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
                                   SharedPreferences.Editor editor = prefs.edit();
                                   editor.putString("first_name", firstname);
                                   editor.putString("last_name", lastname);
                                   editor.apply();

                                   Intent intent= new Intent(getApplicationContext(),Login.class);
                                   startActivity(intent);
                                   finish();
                               } else {

                                   // If sign in fails, display a message to the user.
                                   Toast.makeText(Register.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();

                               }
                           }
                       });
           }
       });

    }
}