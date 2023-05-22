package com.example.dormease;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

        RadioGroup radioGroup = findViewById(R.id.radiogroup_buildings);
        Spinner spinner = findViewById(R.id.floors);

        ArrayAdapter<String> adapterNH = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapterUB = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);



// Set the dropdown layout resource for the adapters
        adapterNH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterUB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the initial adapter for the Spinner
        spinner.setAdapter(adapterNH);

// Add items to adapterA
        adapterNH.add("1st Floor");
        adapterNH.add("2nd Floor");
        adapterNH.add("3rd Floor");
        adapterNH.add("4th Floor");
        adapterNH.add("5th Floor");
        adapterNH.add("6th Floor");

// Add items to adapterB
        adapterUB.add("1st Floor");
        adapterUB.add("2nd Floor");
        adapterUB.add("3rd Floor");
        adapterUB.add("4th Floor");
        adapterUB.add("5th Floor");


// Set the RadioGroup's checked change listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.newhall) {

                    spinner.setAdapter(adapterNH);
                } else if (checkedId == R.id.utility) {

                    spinner.setAdapter(adapterUB);
                }
            }
        });



    }
}
