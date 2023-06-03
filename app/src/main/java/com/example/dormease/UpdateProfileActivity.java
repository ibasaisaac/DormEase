package com.example.dormease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextBuilding, editTextRoom;
    private RadioGroup radioGroupUpdateBuilding;
    private RadioButton radioButtonUpdateSelectedBuilding;
    private String textBuilding, textRoom;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_activity);
    }
}