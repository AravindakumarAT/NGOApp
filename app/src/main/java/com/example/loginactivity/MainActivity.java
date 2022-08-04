package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.loginsignup);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        Intent intent=new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
