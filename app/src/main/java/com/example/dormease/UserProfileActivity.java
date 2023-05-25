package com.example.dormease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewID, textViewEmail, textViewBuilding, textViewRoom;
    private ProgressBar progressBar;
    private String stid, email, room, building;
    private ImageView imageView;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        getSupportActionBar().setTitle("Home");

        textViewEmail = findViewById(R.id.textview_showemail);
        textViewID = findViewById(R.id.textview_showID);
        textViewBuilding = findViewById(R.id.textview_showbuilding);
        textViewRoom = findViewById(R.id.textview_showroom);
        progressBar = findViewById(R.id.progress_bar);

        authProfile = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null)
        {
            Toast.makeText(UserProfileActivity.this, "Something went wrong. ", Toast.LENGTH_SHORT).show();
            
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        //fetch data from database
        String userID = firebaseUser.getUid();

        //extract user reference from "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null)
                {
                    email = firebaseUser.getEmail();
                    stid = readUserDetails.stid;
                    building = readUserDetails.building;
                    room= readUserDetails.roomno;

                    textViewID.setText(stid);
                    textViewEmail.setText(email);
                    textViewBuilding.setText(building);
                    textViewRoom.setText(room);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}