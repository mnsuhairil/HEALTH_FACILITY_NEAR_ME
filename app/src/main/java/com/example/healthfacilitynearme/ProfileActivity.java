package com.example.healthfacilitynearme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private Button Logout;
    private String userID;
    FirebaseDatabase fDatabase;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fDatabase = FirebaseDatabase.getInstance();
        dRef = fDatabase.getReference().child("Users");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.ProfileID);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.ProfileID:
                        return true;
                    case R.id.AboutID:
                        startActivity(new Intent(getApplicationContext()
                                , AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.MapID:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });



        Logout = findViewById(R.id.btnLogoutProf);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfileActivity.this, "Succesfully Logout Account", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();




        final TextView fullnameTextView = findViewById(R.id.fnameViewProf);
        final TextView usernameTextView = findViewById(R.id.unameViewProf);
        final TextView emailTextView = findViewById(R.id.emailViewProf);
        final TextView phoneTextView = findViewById(R.id.atvPhoneProf);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile!=null){
                    String fullname = "WELCOME\n" + userprofile.fullname;
                    String username = userprofile.username;
                    String useremail = userprofile.useremail;
                    String phonenumber = userprofile.phonenumber;

                    fullnameTextView.setText(fullname);
                    usernameTextView.setText(username);
                    emailTextView.setText(useremail);
                    phoneTextView.setText(phonenumber);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Cannot load the profile", Toast.LENGTH_SHORT).show();
            }
        });


        String user_agent = System.getProperty("http.agent");
        Date dateCurrent = Calendar.getInstance().getTime();
        String dateTime = dateCurrent.toString();
        dRef.child(userID).child("datetime").setValue(dateTime);
        dRef.child(userID).child("useragent").setValue(user_agent);

    }

}