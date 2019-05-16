package com.googleapis.roomandme;
import android.content.Intent;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button changePass;
    private Button buttonSignIn;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private static final String TAG = "EmailPassword";
    private ProgressDialog progressDialog;
    private ProgressDialog passSend;
    private static final String EMAIL= "email";
    private LoginButton fbLogin;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        passSend = new ProgressDialog(this);

        fbLogin = findViewById(R.id.login_button);
        fbLogin.setReadPermissions(Arrays.asList(EMAIL));

        changePass= findViewById(R.id.button2);
        buttonSignIn = findViewById(R.id.button3);
        email = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);

        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        int i = view.getId();
        if (i==R.id.button3){
            signIn(email.getText().toString(), password.getText().toString());
        }

        if (i==R.id.button2){
            sendPasswordReset();
        }

        if (i==R.id.login_button){
            fbSignIn();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fbSignIn(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(SignUpActivity.this, Dashboard.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, "Facebook Authentication success.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignUpActivity.this, "Facebook Authentication failed1.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(SignUpActivity.this, "Facebook Authentication failed2.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPasswordReset(){
        if(!validateEmail()){return;}
        passSend.setMessage("Sending to your email...");
        passSend.show();
        mAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Password sent to your email", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        passSend.hide();
                    }
                });
    }

    private boolean validateEmail(){
        boolean valid = true;
        String mail = email.getText().toString();
        if (TextUtils.isEmpty(mail)){
            email.setError("Required");
            valid = false;
        }else {
            email.setError(null);
        }
        return valid;
    }

    private boolean validateForm(){
        boolean valid = true;
        String mail = email.getText().toString();
        if (TextUtils.isEmpty(mail)){
            email.setError("Required");
            valid = false;
        }else{
            email.setError(null);
        }

        String pass = password.getText().toString();
        if (TextUtils.isEmpty(pass)){
            password.setError("Required");
            valid = false;
        }else{
            password.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user){
            progressDialog.hide();

        if (user!=null){
            Intent intent = new Intent(SignUpActivity.this, Dashboard.class);
            startActivity(intent);
        }
    }

    private void signIn(String email, String password){
        Log.d(TAG, "SignIn:" + email);
        if(!validateForm()){return;}
        progressDialog.setMessage("Validating Form...");
        progressDialog.show();

        //Sign in with email
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }
                });
    }
}
