package com.googleapis.roomandme;

import android.app.ProgressDialog;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance(); //initialise firebase auth object

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.button);
        editTextEmail = findViewById(R.id.editText);
        editTextPassword = findViewById(R.id.editText2);
        textViewSignIn = findViewById(R.id.textView2);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);


    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validations are ok
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //user is successfully registered and logged in
                    Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Register.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Could not register", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onClick(View view){
        if (view==buttonRegister){
            registerUser();
        }

        if (view == textViewSignIn){
            //open login activity
            startActivity( new Intent(MainActivity.this, SignUpActivity.class));
        }

    }
}
