package com.example.loginactivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView signupbtn;
    private EditText editName, editEmail, editPassword;
    double totalco2;

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
        String name=editName.getText().toString().trim();
        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        totalco2=0;
        if(name.isEmpty())
        {
            editName.setError("Enter Name");
            editName.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editEmail.setError("Enter Email Id");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editPassword.setError("Enter Password");
            editPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editEmail.setError("Please provide valid email");
            editEmail.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            editPassword.setError("Minimum password length should be 6");
            editPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            User user=new User(name,email,totalco2);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignUp.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUp.this,"Failed to Register! Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SignUp.this,"Failed to Register! Try Again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
