package com.example.dormease;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;



public class roomManagement extends AppCompatActivity {
    int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roombooking);

        System.out.println("Hello");

        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);
        Spinner spinner = findViewById(R.id.floors);
        LinearLayout roomLayout = findViewById(R.id.roomLayout);

        FirebaseDatabase database = FirebaseDatabase.getInstance();





        String[] floors = {"1st","2nd", "3rd" };

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, floors);
        spinner.setAdapter(ad);











        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.newhall) {
                    flag = 0;




                }

                else if (checkedId == R.id.utility) {

                    flag = 1;

                }
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            DatabaseReference roomsRef;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    // Clear the existing buttons in the room layout
                    roomLayout.removeAllViews();

                    // Get the selected floor
                    String selectedFloor = parent.getItemAtPosition(position).toString();

                    if(flag ==0)
                    {
                        roomsRef = database.getReference().child("Buildings/New Hall/Floors/"+selectedFloor+"/Rooms");
                    }
                    else if(flag ==1)
                    {
                        roomsRef = database.getReference().child("Buildings/Utility/Floors/"+selectedFloor+"/Rooms");
                    }

                    // Get the list of rooms for the selected floor
                    //  List<String> roomList = floorRoomMap1.get(selectedFloor);

                    System.out.println("Shuru");
                    List<String> roomList = fetchRooms(roomsRef);

                    System.out.println("Shesh");

                    // Create buttons for each room and add them to the room layout
                    for (String room : roomList) {
                        Button roomButton = new Button(roomManagement.this);
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

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                roomLayout.removeAllViews();

            }
        });

    }

    private List<String> fetchRooms(DatabaseReference roomsRef) {

        List<Room> roomList = new ArrayList<>();

        roomsRef.addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println("Dfirst");
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("Dhuksi");
                    // Extract room data and create Room objects
                    String roomNo = roomSnapshot.child("status").getValue(String.class);
                    // String status = roomSnapshot.child("status").getValue(String.class);

                    Room room = new Room(roomNo);
                    roomList.add(room);


                    System.out.println("Rooms are" + roomList);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        List<String> roomNamesList = new ArrayList<>();

        for (Room room : roomList) {
            String roomName = room.getRoomNo();
            roomNamesList.add(roomName);
        }
        return roomNamesList;
    }


}
