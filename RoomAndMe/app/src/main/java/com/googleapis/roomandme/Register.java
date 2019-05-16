package com.googleapis.roomandme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Register";
    private static final String REQUIRED = "Required";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText name;
    private EditText contact;
    private EditText email;
    private EditText location;
    private Button btnInsert;
    private String userID;
    private String lat, lng;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnInsert = findViewById(R.id.button4);
        name = findViewById(R.id.editText6);
        contact = findViewById(R.id.editText7);
        email = findViewById(R.id.editText8);
        location = findViewById(R.id.editText5);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(Register.this, "Successfully signed in with: " + user.getEmail(), Toast.LENGTH_LONG).show();
                }else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(Register.this, "Successfully signed out", Toast.LENGTH_LONG).show();
                }
            }

        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added information to database: \n" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        btnInsert.setOnClickListener(this);
        //User here is the name of the table
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_op1:
                if (checked)
                    break;
            case R.id.radio_op2:
                if (checked)
                    break;
            case R.id.radio_op3:
                if (checked)
                    break;
            case R.id.radio_op4:
                if (checked)
                    break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v ==btnInsert){
            new GetCoordinates().execute(location.getText().toString().replace(" ", "+"));
            startActivity( new Intent(Register.this, SignUpActivity.class));
        }
    }

    private class GetCoordinates extends AsyncTask<String,Void, String>{
        ProgressDialog dialog = new ProgressDialog(Register.this);
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        @Override
        protected String doInBackground(String... strings){
            String response;
            try{
                String address = location.getText().toString() + "+Singapore";
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyBtCjFhutS8lI3N09ox0FfZOwtcUMmIJ-w", address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            try{
                dialog.dismiss();
                String Name = name.getText().toString();
                String Contact = contact.getText().toString();
                String Email = email.getText().toString();
                String Location = location.getText().toString();
                if (!Name.equals("") && !Contact.equals("") && !Email.equals("") && !Location.equals("")){
                    JSONObject jsonObject = new JSONObject(s);
                    lat =  ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").get("lat").toString();
                    lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").get("lng").toString();
                    u = new User(Name, Email, Contact, lat, lng);
                    myRef.child("Users").child(userID).setValue(u);
                }
                Toast.makeText(Register.this, "Registered successfully. Proceed to Login Now!", Toast.LENGTH_LONG).show();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
