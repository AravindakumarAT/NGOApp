package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
    private TextView continuebtn;
    protected void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_welcome);

        continuebtn=(Button) findViewById(R.id.welbtn);
        continuebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(Welcome.this,Calculation.class));
    }
}
