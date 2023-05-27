package com.example.dormease;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class roombooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roombooking);

       // room_database x = new room_database();
       // x.pushToDatabase();

        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);
        Spinner spinner = findViewById(R.id.floors);
        LinearLayout roomLayout = findViewById(R.id.roomLayout);

      //  ArrayAdapter<String> adapterNH = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
      //  ArrayAdapter<String> adapterUB = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);


        HashMap<String, List<String>> floorRoomMap1 = new HashMap<>();
        floorRoomMap1.put("1st Floor", Arrays.asList("101", "102", "103"));
        floorRoomMap1.put("2nd Floor", Arrays.asList("201", "202", "203"));
        floorRoomMap1.put("3rd Floor", Arrays.asList("301", "302", "303"));

        HashMap<String, List<String>> floorRoomMap2 = new HashMap<>();
        floorRoomMap2.put("3rd Floor", Arrays.asList("301", "302", "303"));
        floorRoomMap2.put("4th Floor", Arrays.asList("401", "402", "403"));
        floorRoomMap2.put("5th Floor", Arrays.asList("501", "502", "503"));

        ArrayAdapter<String> floorAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        floorAdapter1.addAll(floorRoomMap1.keySet());

        ArrayAdapter<String> floorAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        floorAdapter2.addAll(floorRoomMap2.keySet());




//// Set the dropdown layout resource for the adapters
//        adapterUB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//// Set the initial adapter for the Spinner
//        spinner.setAdapter(adapterNH);


//// Add items to adapterB
//        adapterUB.add("1st Floor");
//        adapterUB.add("2nd Floor");
//        adapterUB.add("3rd Floor");
//        adapterUB.add("4th Floor");
//        adapterUB.add("5th Floor");


// Set the RadioGroup's checked change listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.newhall) {

                    spinner.setAdapter(floorAdapter1);
                } else if (checkedId == R.id.utility) {

                    spinner.setAdapter(floorAdapter2);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Clear the existing buttons in the room layout
                roomLayout.removeAllViews();

                // Get the selected floor
                String selectedFloor = parent.getItemAtPosition(position).toString();

                // Get the list of rooms for the selected floor
                List<String> roomList = floorRoomMap1.get(selectedFloor);

                // Create buttons for each room and add them to the room layout
                for (String room : roomList) {
                    Button roomButton = new Button(roombooking.this);
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
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
                roomLayout.removeAllViews();
            }
        });

    }
}
