package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetRecActivity extends AppCompatActivity {

    //Set up db access
    DBHelper db = new DBHelper(GetRecActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_rec);

        //Make all buttons
        Button getRec = findViewById(R.id.GetRec);
        Button goBack = findViewById(R.id.BackToMainRec);
        Button okay = findViewById(R.id.OkayButton);
        Button lessReps = findViewById(R.id.LessReps);
        Button moreReps = findViewById(R.id.MoreReps);
        Button lessWeight = findViewById(R.id.LessWeight);
        Button moreWeight = findViewById(R.id.MoreWeight);
        Button newWorkout = findViewById(R.id.New);

        //Make spinner
        Spinner workout_types = findViewById(R.id.GetRecSpinner);

        //Set up text fields
        TextView howWent = findViewById(R.id.HowWent);
        TextView recText = findViewById(R.id.RecText);

        WorkoutData WorkoutRec = null;

        //By Default set some stuff invisible
        Button[] toggles = {lessReps,okay,lessWeight,moreReps,moreWeight,newWorkout};
        //Button[] secondary = {reps, weight};

        toggle(toggles);
        //toggle(secondary);

        howWent.setVisibility(View.INVISIBLE);
        recText.setVisibility(View.INVISIBLE);




        //Fill the spinner with the right items:
        ArrayList<String> arraySpinner = db.getTypes("");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workout_types.setAdapter(adapter);

        //Add getRec functionality:
        getRec.setOnClickListener(v -> {
            recText.setVisibility(View.INVISIBLE);
            //Make sure rec is gettable
            if(db.getLast(workout_types.getSelectedItem().toString()) != null){
                howWent.setVisibility(View.VISIBLE);
                toggle(toggles);
            } else {
                Toast.makeText(GetRecActivity.this, "No Workout Data", Toast.LENGTH_SHORT).show();
            }
        });

        lessReps.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), -1, toggles, recText, howWent));
        okay.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), 0, toggles, recText, howWent));
        lessWeight.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), 1, toggles, recText, howWent));
        moreReps.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), -2, toggles, recText, howWent));
        moreWeight.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), 2, toggles, recText, howWent));
        newWorkout.setOnClickListener(v -> recommend(workout_types.getSelectedItem().toString(), 3, toggles, recText, howWent));

        goBack.setOnClickListener(v -> startActivity(new Intent(GetRecActivity.this, MainActivity.class)));

    }

    private void toggle(Button[] b){
        for (int i = 0; i < b.length; i++){
            if(b[i].getVisibility() == View.VISIBLE){
                b[i].setVisibility(View.INVISIBLE);
                b[i].setEnabled(false);
            } else {
                b[i].setVisibility(View.VISIBLE);
                b[i].setEnabled(true);
            }
        }
    }


    //-1 = lessReps, 0 = okay, 1 = lessWeight, -2 = moreReps, 2 = moreWeight
    //(1) Toggles button visibility
    //(2) Gets WorkoutData, makes sure it isn't null
    //(3) Creates rec, puts it in rec text
    //(4) Makes recText visible


    //ADD TO
    private void recommend(String s, int i, Button[] b, TextView t, TextView howWent){
        toggle(b);
        WorkoutData temp = db.getLast(s);
        howWent.setVisibility(View.INVISIBLE);
        //Make sure there is data - even though there is another check, this can still be null
        if(temp == null){
            Toast.makeText(GetRecActivity.this, "No Workout Data", Toast.LENGTH_SHORT).show();
            return;
        }

        String theRec = "default";

        //Create rec
        if(i == -1) //lessReps
        {
            theRec = "Okay, let's stay at" + (temp.getWeight()) + "lbs but do " + (temp.getReps() - 2) + " reps instead.";
        }
        else if (i == 1) //lessWeight
        {
            theRec = "That's fine, let's go down to " + (temp.getWeight() - 5) + "lbs next time with the same " + (temp.getReps()) + " reps.";
        }
        else if (i == 0) //okay (will change)
        {
            theRec = "Try staying at " + (temp.getWeight()) + "lbs next time, but try to push " + (temp.getReps() + 1) + " reps next time.";
        }
        else if (i == -2) //moreReps
        {
            theRec = "Try staying at " + (temp.getWeight()) + "lbs but do " + (temp.getReps() + 2) + " reps.";
        }
        else if (i == 2) //moreWeight
        {
            theRec = "Nice progress! go up to " + (temp.getWeight() + 5) + "lbs next time with " + (temp.getReps()) + " reps.";
        }
        else if (i == 3) //randomly select new workout & add to database
        {

        }

        //Put in rec text and make visible
        t.setText(theRec);
        t.setVisibility(View.VISIBLE);
    }

}