package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register, forgotPassword;
    private EditText editemail,editpassword;
    private Button login;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.loginsignup);
        register.setOnClickListener(this);

        login=(Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(this);

        editemail=(EditText) findViewById(R.id.loginemail);
        editpassword= (EditText) findViewById(R.id.loginpassword);

        mAuth=FirebaseAuth.getInstance();

        forgotPassword=(TextView) findViewById(R.id.forgotpass);
        forgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.loginsignup:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.loginbtn:
                userLogin();
                break;
            case R.id.forgotpass:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

        }
    }

    private void userLogin()
    {
        String email=editemail.getText().toString().trim();
        String password=editpassword.getText().toString().trim();
        if(email.isEmpty())
        {
            editemail.setError("Email is required!");
            editemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editemail.setError("Please enter a valid email!");
            editemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editpassword.setError("Password is required!");
            editpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editpassword.setError("Min password length is 6 characters!");
            editpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this,Welcome.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
