package com.example.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText yEmail,yPassword;
    Button yRegBtn;
    TextView yLogBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        yEmail = findViewById(R.id.Email);
        yPassword = findViewById(R.id.Password);
        yRegBtn = findViewById(R.id.RegBtn);
        yLogBtn = findViewById(R.id.LogBtn);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            Intent i = new Intent(Register.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        yLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        yRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = yEmail.getText().toString().trim();
                String password = yPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    yEmail.setError("Input your email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    yPassword.setError("Input your password");
                }else if(password.length() < 7){
                    yPassword.setError("Password must be strong");
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User is created",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register.this,HomePage.class);
                            startActivity(i);

                        }else{
                            Toast.makeText(Register.this,"Error input" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });
    }
}