package com.example.dormease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPass;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().setTitle("Login");

        editTextLoginEmail = findViewById(R.id.edittext_login_email);
        editTextLoginPass = findViewById(R.id.edittext_login_pass);
        progressBar = findViewById(R.id.progress_bar);

        //to check if user already logged in or not
        authProfile = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.loginbtn);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textemail = editTextLoginEmail.getText().toString();
                String textpass = editTextLoginPass.getText().toString();

                if(TextUtils.isEmpty(textemail)){
                    Toast.makeText(LoginActivity.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Email required");
                    editTextLoginEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(textpass)){
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    editTextLoginPass.setError("Password required");
                    editTextLoginPass.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textemail,textpass);
                }
            }
        });

    }

    private void loginUser(String textemail, String textpass) {

        authProfile.signInWithEmailAndPassword(textemail,textpass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //check if email verified
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "You are logged in.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }

                }
                else
                {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e)
                    {
                        editTextLoginEmail.setError("User does not exist or no longer valid. Please register again.");
                        editTextLoginEmail.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextLoginEmail.setError("Invalid credentials. Kindly check and re-enter.");
                        editTextLoginEmail.requestFocus();
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        //setup alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify email now.");

        //open email app if user press continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                //opens the list of email apps on the phone
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                //email opened in new window, not withiin our app
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //check if user logged in or not.
    @Override
    protected void onStart(){
        super.onStart();
        if(authProfile.getCurrentUser() != null)
        {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "You can log in now", Toast.LENGTH_SHORT).show();
        }
    }

}