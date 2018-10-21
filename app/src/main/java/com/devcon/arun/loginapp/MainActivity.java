package com.devcon.arun.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button loginBtn, newAccountBtn;
    TextView forgotPasswordTV, messageTV;
    EditText emailField, passwordField;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginButton);
        newAccountBtn = findViewById(R.id.newAccountButton);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTextView);
        messageTV = findViewById(R.id.invalidCredentialMessage);
        emailField = findViewById(R.id.emailText);
        passwordField = findViewById(R.id.passwordTextLogin);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(emailField, passwordField)) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                            messageTV.setVisibility(View.VISIBLE);
                        }
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public static boolean validateForm(EditText emailField, EditText passwordField) {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //((TextView) findViewById(R.id.textSignInStatus)).setText("User ID: " + user.getUid());
            startActivity(new Intent(MainActivity.this, Home.class));
        } else {
            //((TextView) findViewById(R.id.textSignInStatus)).setText("Error: sign in failed.");


            //loginBtn = (Button) findViewById(R.id.loginButton);
            loginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(MainActivity.this, Home.class));
                    signIn(emailField.getText().toString(), passwordField.getText().toString());
                }
            });

            //newAccountBtn = (Button) findViewById(R.id.newAccountButton);
            newAccountBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, NewAccount.class));
                }
            });

            //forgotPasswordTV = (TextView)  findViewById(R.id.forgotPasswordTextView);
            forgotPasswordTV.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                }
            });
        }
    }

}
