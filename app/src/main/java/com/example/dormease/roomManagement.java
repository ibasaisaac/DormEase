package com.example.dormease;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


public class roomManagement extends AppCompatActivity{

    int flag;
    String selectedBuilding;
    List<String> roomsList;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public void setFloor(String selectedBuilding)
    {
        this.selectedBuilding = selectedBuilding;
    }

    public interface RoomsCallback {
        void onRoomsFetched(List<String> roomList);
    }

    public CompletableFuture<List<String>> getRooms(String selectedFloor) throws InterruptedException {


        CompletableFuture<List<String>> future = new CompletableFuture<>();
        List<String> roomList = new ArrayList<>();
        DatabaseReference roomsRef = database.getReference("Buildings/" + selectedBuilding +"/Floors/" + selectedFloor + "/Rooms");


        roomsRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot roomsSnapshot: dataSnapshot.getChildren())
                {
                    String room = roomsSnapshot.getKey();
                    roomList.add(room);
                }
                future.complete(roomList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });


        return future;
    }


}
