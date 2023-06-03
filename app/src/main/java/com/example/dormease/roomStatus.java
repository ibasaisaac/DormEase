package com.example.dormease;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class roomStatus  extends AppCompatActivity {

    List<String> getRoomInfo(Button roomButton, String selectedBuilding, String selectedFloor, String selectedRoom)
    {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomRef = database.getReference("Buildings/" + selectedBuilding + "/Floors/" + selectedFloor + "/Rooms/" + selectedRoom);

    List<String> residents = null;

 //       Button roomButton = findViewById(R.id.roomButton);

        roomButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot roomSnapshot) {




                        String resident1 = roomSnapshot.child("resident1").getValue(String.class);
                        String resident2 = roomSnapshot.child("resident1").getValue(String.class);
                        String resident3 = roomSnapshot.child("resident1").getValue(String.class);
                        String resident4 = roomSnapshot.child("resident1").getValue(String.class);
                        String status = roomSnapshot.child("status").getValue(String.class);
               residents.add(resident1);
               residents.add(resident2);
               residents.add(resident3);
               residents.add(resident4);
               residents.add(status);


                // Use the floorsList as needed
                // For example, you can pass it to an adapter for display in a ListView or RecyclerView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any potential errors
            }
        });
        }
    });


        return residents;
    }
    }

