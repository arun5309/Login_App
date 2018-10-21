package com.devcon.arun.loginapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class NewAccount extends AppCompatActivity {

    TextView tandcTV;
    Button registerBtn;
    EditText emailField, passwordField, confirmPasswordField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailText);
        passwordField = findViewById(R.id.passwordTextLogin);
        confirmPasswordField = findViewById(R.id.confirmPasswordText);


        tandcTV = (TextView)  findViewById(R.id.tandcText);
        tandcTV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam";

                AlertDialog.Builder builder = new AlertDialog.Builder(NewAccount.this);
                builder.setTitle("My Title");
                builder.setMessage(loremIpsum);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                }
                });
                builder.show();
            }
        });

        registerBtn = findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString(), password = passwordField.getText().toString(), confirmPassword = confirmPasswordField.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(NewAccount.this, "Email and password must be non-empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(confirmPassword)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(NewAccount.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "createUserWithEmail:success");

                                        Toast.makeText(NewAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(NewAccount.this, MainActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(NewAccount.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Password and confirm password differ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
