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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class roomManagement2  extends AppCompatActivity{

    int flag;

    List<String> roomsList;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_roombooking);

        System.out.println("Hello");

        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);

        LinearLayout roomLayout = findViewById(R.id.roomLayout);

        Spinner spinner = findViewById(R.id.floors);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.newhall){

                    spinner.setAdapter(getFloors("New Hall"));

                }

                else if (checkedId == R.id.utility) {

                    spinner.setAdapter(getFloors("Utility"));

                }

            }


        });

    }

    private ArrayAdapter<String> getFloors(String selectedBuilding) {

        List<String> floorList = new ArrayList<>();
        DatabaseReference floorsRef = database.getReference("Buildings/" + selectedBuilding +"/Floors");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

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
