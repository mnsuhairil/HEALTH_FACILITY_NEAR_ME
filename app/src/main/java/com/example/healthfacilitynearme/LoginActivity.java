package com.example.healthfacilitynearme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private  Button button;
    EditText e1, e2;
    EditText inputEmail, inputPassword;
    String email, password;
    Button btnLogin;
    private FirebaseAuth mAuth;
    ProgressBar progresBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail=findViewById(R.id.atvEmailLog);
        inputPassword=findViewById(R.id.atvPasswordLog);
        btnLogin=findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        progresBar =  findViewById(R.id.progressBarLog);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }



    private void checkLogin(){
        
        progresBar.setVisibility(View.VISIBLE);

        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();

        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(LoginActivity.this, "email or password cannot empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progresBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login Unsuccessful, Email or Password ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public void signUpMethod (View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}