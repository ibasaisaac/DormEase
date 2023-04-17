package com.example.dormease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submit);
        EditText name = findViewById(R.id.name);
        EditText id = findViewById(R.id.stid);
        EditText room = findViewById(R.id.room);
        EditText building = findViewById(R.id.block);
        EditText complain = findViewById(R.id.complain);
        EditText time = findViewById(R.id.time);

        submit.setEnabled(false);

        final String TAG = "MainActivity";

        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String s_name = name.getText().toString();
                String s_id = id.getText().toString();
                String s_room = room.getText().toString();
                String s_building = building.getText().toString();
                String s_complain = complain.getText().toString();
                String s_time = time.getText().toString();

                submit.setEnabled(!s_name.isEmpty() && !s_id.isEmpty() && !s_room.isEmpty() && !s_building.isEmpty() && !s_complain.isEmpty());


                if(!submit.isEnabled())
                {
                    submit.setBackgroundColor(Color.parseColor("#6d7870"));
                    submit.setTextColor(Color.parseColor("#323633"));
                }
                else{
                    submit.setBackgroundColor(Color.parseColor("#db4f2c"));
                    submit.setTextColor(Color.parseColor("#d3eaf2"));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }


        };

        name.addTextChangedListener(textWatcher);
        id.addTextChangedListener(textWatcher);
        room.addTextChangedListener(textWatcher);
        building.addTextChangedListener(textWatcher);
        complain.addTextChangedListener(textWatcher);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String s_name = name.getText().toString();
                String s_id = id.getText().toString();
                String s_room = room.getText().toString();
                String s_building = building.getText().toString();
                String s_complain = complain.getText().toString();
                String s_time = time.getText().toString();

                Dataholder obj = new Dataholder(s_name, s_id, s_room, s_building, s_complain);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("complaints");
                node.child(s_room).setValue(obj);

                name.setText("");
                id.setText("");
                room.setText("");
                building.setText("");
                complain.setText("");
                time.setText("");

                Toast.makeText(MainActivity.this, "Complaint sent", Toast.LENGTH_SHORT).show();



            }
        });

    }

}
