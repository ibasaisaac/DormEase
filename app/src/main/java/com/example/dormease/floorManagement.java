package com.example.dormease;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class floorManagement extends AppCompatActivity{

    int flag;
    String selectedBuilding;
    roomManagement r = new roomManagement();
    List<String> roomsList;
    String roomName;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomRef;

    public String getRoomName(){
        return roomName;
    }
    public DatabaseReference getRef()
    {
        return this.roomRef;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        room_database room = new room_database();
//        room.pushToDatabase();

        setContentView(R.layout.activity_roombooking);


        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);

       // LinearLayout roomLayout = findViewById(R.id.roomLayout);
        GridLayout roomGridView = findViewById(R.id.roomGridLayout);

        Spinner spinner = findViewById(R.id.floors);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.newhall) {

                    selectedBuilding = "New Hall";
//                    spinner.setAdapter(getFloors("New Hall"));
                    spinner.setAdapter(getFloors());

                } else if (checkedId == R.id.utility) {

                    selectedBuilding = "Utility";
//                    spinner.setAdapter(getFloors("Utility"));
                    spinner.setAdapter(getFloors());

                }

            }


        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Code to handle the selected item


                String selectedFloor = parent.getItemAtPosition(position).toString();

//                CompletableFuture<List<String>> future = null;
//                try {
//                    future = r.getRooms(selectedFloor);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                //    List<String> roomList = r.getRooms(selectedFloor);


                try {
                    r.getRooms(selectedFloor).thenAccept(roomList -> {


                        roomGridView.removeAllViews();

                                for (String room : roomList) {

                                    Button roomButton = new Button(floorManagement.this);
                                    roomButton.setText(room);
//                                    roomButton.setBackgroundColor(Color.parseColor("#FF7956"));
//
//                                    roomButton.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            // Handle button click event and show room details
//                                            roomName = ((Button) v).getText().toString();
//
//                                            //  showRoomDetails(roomName);
//                                        }
//                                    });

                                    roomButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                    public void onClick (View v){
                                        roomName = ((Button) v).getText().toString();
                                        roomRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomName);

                                            Intent startIntent = new Intent(getApplicationContext(), com.example.dormease.roomInfoPage.class);
                                            startActivity(startIntent);

                                        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    // Get the status value from the fetched data
                                                    int status = dataSnapshot.child("status").getValue(Integer.class);

                                                    // Set the button color based on the status value
                                                    if (status == 0) {
                                                        // Green color
                                                        roomButton.setBackgroundColor(Color.parseColor("#FF7956"));
                                                    } else if (status == 1) {
                                                        // Blue color
                                                        roomButton.setBackgroundColor(Color.parseColor("#0079F6"));
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                // Handle any potential errors
                                            }
                                        });



                                    }
                                });


                                    roomGridView.addView(roomButton);
                                }

                            }
                    );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Code to handle when no item is selected
            }
        });



    }

    private ArrayAdapter<String> getFloors() {

        List<String> floorList = new ArrayList<>();
        DatabaseReference floorsRef = database.getReference("Buildings/" + selectedBuilding +"/Floors");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);


       r.setFloor(selectedBuilding);
        floorsRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot floorsSnapshot: dataSnapshot.getChildren())
                {
                    String floor = floorsSnapshot.getKey();
                    floorList.add(floor);
                }

                adapter.addAll(floorList);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return adapter;
    }


}
