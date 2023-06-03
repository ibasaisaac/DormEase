package com.example.dormease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Handler h = new Handler();
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to check if user already logged in or not
        authProfile = FirebaseAuth.getInstance();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser firebaseUser = authProfile.getCurrentUser();

                if(firebaseUser != null)
                {
                    if(firebaseUser.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                }
                else {
                    Intent i = new Intent(MainActivity.this, FirstActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);
    }
}