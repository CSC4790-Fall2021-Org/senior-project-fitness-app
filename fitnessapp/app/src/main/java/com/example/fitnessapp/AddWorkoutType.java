package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWorkoutType extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_type);

        //Instantiate button
        Button goBack = findViewById(R.id.BackToAdd);
        Button submit = findViewById(R.id.SubmitWOType);

        EditText exercise = findViewById(R.id.WorkoutString);

        //Instantiate DB
        DBHelper db = new DBHelper(AddWorkoutType.this);

        //Make Sure the user can go back
        goBack.setOnClickListener(v -> startActivity(new Intent(AddWorkoutType.this, AddDataActivity.class)));


        //Send info to database on submit:
        //Make Sure the user can go back
        submit.setOnClickListener(v -> {
            if(!exercise.getText().toString().trim().equals("")){
                //Input field is not empty
                if (db.addType(exercise.getText().toString())){
                    Toast.makeText(AddWorkoutType.this, "Exercise Added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddWorkoutType.this, "Error, could not add exercise", Toast.LENGTH_SHORT).show();
                }
                exercise.setText("");
            } else {
                Toast.makeText(AddWorkoutType.this, "Please input a workout", Toast.LENGTH_SHORT).show();
            }
        });
    }

}