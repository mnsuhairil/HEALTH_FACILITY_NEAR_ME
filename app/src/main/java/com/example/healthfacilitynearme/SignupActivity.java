package com.example.healthfacilitynearme;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText inputEmail,pass1,pass2,fullname,userName,Phone;
    Button btnRegister;
    ProgressBar progressBar;

    FirebaseDatabase fDatabase;
    DatabaseReference dRef;
    String password;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail=findViewById(R.id.atvEmailReg);
        pass1=findViewById(R.id.atvPasswordReg);
        pass2=findViewById(R.id.confPwd);
        fullname=findViewById(R.id.atvFullnameReg);
        userName=findViewById(R.id.atvUsernameReg);
        btnRegister=findViewById(R.id.btnSignUp);
        progressBar=findViewById(R.id.progressBarReg);
        Phone = findViewById(R.id.atvPhoneReg);


        fDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dRef = fDatabase.getReference().child("Users");


        btnRegister.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            String uemail=inputEmail.getText().toString();
            String password1=pass1.getText().toString();
            String password2=pass2.getText().toString();
            String fname = fullname.getText().toString();
            String uname = userName.getText().toString();
            String uPhone = Phone.getText().toString();


            if (uname.isEmpty()){
                Toast.makeText(SignupActivity.this, "User Name is required", Toast.LENGTH_SHORT).show();
                userName.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if (fname.isEmpty()){
                Toast.makeText(SignupActivity.this, "Full Name is required", Toast.LENGTH_SHORT).show();
                fullname.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if (uemail.isEmpty()){
                Toast.makeText(SignupActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                inputEmail.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if (password1.isEmpty()||password2.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                pass1.requestFocus();
                pass2.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if (password1.length()<6||password2.length()<6){
                Toast.makeText(SignupActivity.this, "Password Must Be More or Equal than 6 Characters", Toast.LENGTH_SHORT).show();
                pass1.requestFocus();
                pass2.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if (!password1.equals(password2)){
                Toast.makeText(SignupActivity.this, "Password and confirm password must be same", Toast.LENGTH_SHORT).show();
                pass1.requestFocus();
                pass2.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            else if(password1==password2){
                password = password1;
            }


                mAuth.createUserWithEmailAndPassword(uemail,password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(fname,uname,uemail,uPhone);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignupActivity.this, "User created succesfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(SignupActivity.this, "Fail to register! Try again!", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(getApplicationContext(),"Fail to register! Try again!",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });

            if (uname!=null|| fname!=null ||uemail!=null){
            }

        });

    }


    public void loginMethod (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}