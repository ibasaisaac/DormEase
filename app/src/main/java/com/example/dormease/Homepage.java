package com.example.dormease;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button homeb3 = findViewById(R.id.homeb3);
        homeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent1 = new Intent(getApplicationContext(), floorManagement.class);
                startActivity(startIntent1);
            }
        });
        Button homeb2 = findViewById(R.id.homeb2);
        homeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent2 = new Intent(getApplicationContext(), com.example.dormease.complainform.class);
                startActivity(startIntent2);
            }
        });
        Button homeb1 = findViewById(R.id.homeb1);
        homeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent3 = new Intent(getApplicationContext(), com.example.dormease.noticeboard.class);
                startActivity(startIntent3);
            }
        });

    }
}