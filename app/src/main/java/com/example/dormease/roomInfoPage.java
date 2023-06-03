package com.example.dormease;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

    public class roomInfoPage extends AppCompatActivity {


        @Override
        public Context getApplicationContext() {
            return super.getApplicationContext();
        }

        String roomNo;
        List<String> roomInfo = null;
        String status;
        floorManagement floors = new floorManagement();
        roomStatus roomStatus = new roomStatus();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref;




        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            TextView roomStatusTextView = findViewById(R.id.roomStatusTextView);
            TextView resident1NameTextView = findViewById(R.id.resident1NameTextView);
            TextView resident1IdTextView = findViewById(R.id.resident1IdTextView);

            TextView resident2NameTextView = findViewById(R.id.resident2NameTextView);
            TextView resident2IdTextView = findViewById(R.id.resident2IdTextView);

            TextView resident3NameTextView = findViewById(R.id.resident3NameTextView);
            TextView resident3IdTextView = findViewById(R.id.resident3IdTextView);

            TextView resident4NameTextView = findViewById(R.id.resident4NameTextView);
            TextView resident4IdTextView = findViewById(R.id.resident4IdTextView);




            roomNo = floors.getRoomName();
            ref= floors.getRef();

//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // Get the status value from the fetched data
//                        int status = dataSnapshot.child("status").getValue(Integer.class);
//                        roomStatusTextView.setText(status);
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Handle any potential errors
//                }
//            });




                setContentView(R.layout.roominfo);


            }
        }





