package com.example.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView signupbtn;
    private EditText editName, editEmail, editPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth; //declare and initialize our Auth
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        signupbtn = (Button) findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(this);

        editEmail=(EditText) findViewById(R.id.signupemail);
        editName=(EditText) findViewById(R.id.signupname);
        editPassword=(EditText) findViewById(R.id.signuppassword);
    }

    @Override
    public void onClick(View v)
    {

    }
}
