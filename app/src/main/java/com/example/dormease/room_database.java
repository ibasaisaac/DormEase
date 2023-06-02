package com.example.dormease;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class room_database {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference roomsRef = database.getReference("Buildings/Utility/Floors/5th/Rooms");
    String[] roomNames = {"501", "502", "503", "504", "505", "506", "507", "508", "509", "510"};



                    void pushToDatabase ()
                    {

                        for (String roomName : roomNames) {
                            int status = 0;
                            // Set the initial status for each room

                            // Generate a unique key using push()
                            //    String roomKey = roomsRef.push().getKey();

                            // Push the room with its status
                            //   roomsRef.child(roomKey).child("status").setValue(status);


                            roomsRef.child(roomName).addListenerForSingleValueEvent(new ValueEventListener() {

                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // Room already exists, handle accordingly
                                        // You can show an error message or update the existing room
                                    } else {
                                        // Room doesn't exist, add it to the database
                                        DatabaseReference newRoomRef = roomsRef.child(roomName);

                                        newRoomRef.child("status").setValue(status);
                                        newRoomRef.child("residents/resident1").setValue(true);
                                        newRoomRef.child("residents/resident2").setValue(true);
                                        newRoomRef.child("residents/resident3").setValue(true);
                                        newRoomRef.child("residents/resident4").setValue(true);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle any potential errors
                                }

                            });


                        }
                    }

}

