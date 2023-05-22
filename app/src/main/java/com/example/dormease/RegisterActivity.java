package com.example.dormease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegName, editTextRegEmail, editTextRegPassword,
            editTextRegStid, editTextRegRoomno;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegBuilding;
    private RadioButton radioButtonRegBuildingSelected;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().setTitle("Register");

        Toast.makeText(RegisterActivity.this, "Create account", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progress_bar);
        editTextRegName = findViewById(R.id.edittext_reg_fullname);
        editTextRegEmail = findViewById(R.id.edittext_reg_email);
        editTextRegStid = findViewById(R.id.edittext_reg_stid);
        editTextRegPassword = findViewById(R.id.edittext_reg_password);
        editTextRegRoomno = findViewById(R.id.edittext_reg_roomno);
        radioGroupRegBuilding = findViewById(R.id.radiogroup_reg_building);
        radioGroupRegBuilding.clearCheck();

        Button buttonReg = findViewById(R.id.regbtn);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedBuildingID = radioGroupRegBuilding.getCheckedRadioButtonId();
                radioButtonRegBuildingSelected = findViewById(selectedBuildingID);

                String textname = editTextRegName.getText().toString();
                String textemail = editTextRegEmail.getText().toString();
                String textstid = editTextRegStid.getText().toString();
                String textpass = editTextRegPassword.getText().toString();
                String textbuilding; //cant obtain value before checking if any radio btn selected or not
                String textroomno = editTextRegRoomno.getText().toString();

                if(TextUtils.isEmpty(textname)){
                    Toast.makeText(RegisterActivity.this, "Please enter full name", Toast.LENGTH_LONG).show();
                    editTextRegName.setError("Full name required");
                    editTextRegName.requestFocus();
                }else if(TextUtils.isEmpty(textemail)){
                    Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
                    editTextRegEmail.setError("Email required");
                    editTextRegEmail.requestFocus();
                }
                else if(!textemail.matches("^\\S+@iut-dhaka\\.edu$")){
                    Toast.makeText(RegisterActivity.this, "Please re-enter email", Toast.LENGTH_LONG).show();
                    editTextRegEmail.setError("Valid Email required");
                    editTextRegEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(textstid)){
                    Toast.makeText(RegisterActivity.this, "Please enter student ID", Toast.LENGTH_LONG).show();
                    editTextRegStid.setError("Student ID required");
                    editTextRegStid.requestFocus();
                } else if(TextUtils.isEmpty(textpass)){
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    editTextRegPassword.setError("Password required");
                    editTextRegPassword.requestFocus();
                } else if(textpass.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                    editTextRegPassword.setError("Password should be at least 6 characters");
                    editTextRegPassword.requestFocus();
                }else if(radioGroupRegBuilding.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegisterActivity.this, "Please select building", Toast.LENGTH_LONG).show();
                    radioButtonRegBuildingSelected.setError("Select your building");
                    radioButtonRegBuildingSelected.requestFocus();
                } else if(TextUtils.isEmpty(textroomno)) {
                    Toast.makeText(RegisterActivity.this, "Enter Room number", Toast.LENGTH_LONG).show();
                    editTextRegRoomno.setError("Room number is required");
                    editTextRegRoomno.requestFocus();
                }
                else //everything ok
                {
                    textbuilding = radioButtonRegBuildingSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textname,textemail,textstid,textbuilding,textroomno,textpass);
                }
            }
        });

    }

    private void registerUser(String textname, String textemail, String textstid, String textbuilding, String textroomno, String textpass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textemail,textpass).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("ok");
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //enter user data into realtime database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textname,textstid,textbuilding,textroomno);

                    //extracting user reference from database for "registered users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    //oncompletelistener to make sure successfully committed to database or not
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                //send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this, "User registered successfully. Please verify email.", Toast.LENGTH_LONG).show();

//                                //open login page after successful reg
//                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                //to prevent user from returning back to register activity after registration
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                startActivity(intent);
//                                finish(); //close register activity
                            }else{
                                Toast.makeText(RegisterActivity.this, "User registered failed.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else{
                    // If reg fails, display a message to the user.
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthEmailException e){
                        editTextRegEmail.setError("jhamela");
                        editTextRegEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegEmail.setError("jhamela2");
                        editTextRegEmail.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegEmail.setError("Already registered user");
                        editTextRegEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}