package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare the three buttons used in this activity
         Button addButton = findViewById(R.id.AddDataButon);
         Button viewButton = findViewById(R.id.ViewDataButton);
         Button recButton = findViewById(R.id.GetRecButton);

        //Now have them do stuff
        addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddDataActivity.class)));

        viewButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewDataActivity.class)));

        recButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GetRecActivity.class)));
    }

}