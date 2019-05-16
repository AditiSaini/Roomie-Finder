package com.googleapis.roomandme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        home = findViewById(R.id.button6);
        home.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if (view==home){
            Intent intent = new Intent(LocationActivity.this, Dashboard.class);
            startActivity(intent);
        }
    }
}
