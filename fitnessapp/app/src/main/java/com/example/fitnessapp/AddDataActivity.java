package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        //Declare Buttons and input fields
        Button goBack = findViewById(R.id.BackToMain);
        Button submit = findViewById(R.id.NewWOSubmit);
        Button addType = findViewById(R.id.AddType);

        EditText reps = findViewById(R.id.AddReps);
        EditText weight = findViewById(R.id.AddWeight);
        Spinner workout_types = findViewById(R.id.spinner);

        //Put in some default values:
        reps.setText("0");
        weight.setText("0");

        //Instantiate DB
        DBHelper db = new DBHelper(AddDataActivity.this);


        //Make Sure the user can go back
        goBack.setOnClickListener(v -> startActivity(new Intent(AddDataActivity.this, MainActivity.class)));

        //Let user add a new workout type:
        addType.setOnClickListener(v -> startActivity(new Intent(AddDataActivity.this, AddWorkoutType.class)));

        //Give Submit Button Functionality
        submit.setOnClickListener(v -> {
            //I am able to access Vals, now I just need to send them to the db.

            String t = workout_types.getSelectedItem().toString();
            int r = Integer.parseInt(reps.getText().toString());
            int w = Integer.parseInt(weight.getText().toString());
            WorkoutData newWorkout = new WorkoutData(t,r,w);

            //MUST BE ADDED AS: TYPES, REPS, WEIGHT
            if( db.addWorkout(newWorkout)){
                Toast.makeText(AddDataActivity.this, "Workout Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddDataActivity.this, "Error, could not add workout", Toast.LENGTH_SHORT).show();
            }
            reps.setText("0");
            weight.setText("0");
        });


        //Give the spinner values All from db
        ArrayList<String> arraySpinner = db.getTypes("");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workout_types.setAdapter(adapter);
    }

}