package com.example.dormease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewID, textViewEmail, textViewBuilding, textViewRoom, textViewName;
    private ProgressBar progressBar;
    private String stid, email, room, building, name;
    private FirebaseAuth authProfile;

    //navbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

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
        textViewName = findViewById(R.id.textview_showName);

        authProfile = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null)
        {
            Toast.makeText(UserProfileActivity.this, "Something went wrong. ", Toast.LENGTH_SHORT).show();
            
        }
        else{
            //if user coming to app after registering but before validating email
            checkIfEmailVerified(firebaseUser);

            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        //navbar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_profile);
    }

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        //setup alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify email now.");

        //open email app if user press continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                //opens the list of email apps on the phone
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                //email opened in new window, not withiin our app
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                    name =  firebaseUser.getDisplayName().toUpperCase();
                    stid = readUserDetails.stid;
                    building = readUserDetails.building;
                    room= readUserDetails.roomno;

                    textViewID.setText(stid);
                    textViewEmail.setText(email);
                    textViewBuilding.setText(building);
                    textViewRoom.setText(room);
                    textViewName.setText(name);
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

    //navbar
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                break;
            case R.id.nav_home:
                Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //creating hamburger
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //inflate menu items
//        getMenuInflater().inflate(R.menu.common_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    //when menu selected
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.menu_home){
//            Intent intent = new Intent(.this, HomeActivity.class);
//            startActivity(intent);
//        }
//        else if(id == R.id.menu_home){
//            Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}