package com.example.dormease;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        room_database room = new room_database();
//        room.pushToDatabase();

        setContentView(R.layout.activity_roombooking);


        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);

        LinearLayout roomLayout = findViewById(R.id.roomLayout);

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


                        roomLayout.removeAllViews();

                                for (String room : roomList) {

                                    Button roomButton = new Button(floorManagement.this);
                                    roomButton.setText(room);
                                    roomButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Handle button click event and show room details
                                            String roomName = ((Button) v).getText().toString();
                                            //  showRoomDetails(roomName);
                                        }
                                    });
                                    roomLayout.addView(roomButton);
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
