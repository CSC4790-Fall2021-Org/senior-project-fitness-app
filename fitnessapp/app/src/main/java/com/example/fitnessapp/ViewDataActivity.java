package com.example.fitnessapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewDataActivity extends AppCompatActivity {

    //Link to db
    DBHelper db = new DBHelper(ViewDataActivity.this);

    //Load all values on page load:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Button goBack = findViewById(R.id.BackToMain_View);
        Button update = findViewById(R.id.UpdateButton);
        Button delete = findViewById(R.id.DelSelected);

        Spinner spin = findViewById(R.id.ViewSpinner);
        ListView listOfWorkouts = findViewById(R.id.WorkoutList);

        //Store list of workout data:
        ArrayList<Integer> dataToBeDeleted = new ArrayList<>();

        //Make initial list
        updateList(listOfWorkouts,"");


        goBack.setOnClickListener(v -> startActivity(new Intent(ViewDataActivity.this, MainActivity.class)));

        update.setOnClickListener(v -> updateList(listOfWorkouts, spin.getSelectedItem().toString()));

        //Let the spinner see all db values
        ArrayList<String> arraySpinner = db.getTypes("");
        arraySpinner.add(0,"All");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        //Get Delete Button to Work
        //Make listener for listView
        // ListView on item selected listener.
        listOfWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WorkoutData selectedFromList = (WorkoutData) listOfWorkouts.getItemAtPosition(position);
                Integer SelectedID = selectedFromList.getID();

                if(dataToBeDeleted.contains(SelectedID)){
                    dataToBeDeleted.remove(SelectedID);
                    Toast.makeText(ViewDataActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                }else{
                    dataToBeDeleted.add(SelectedID);
                    Toast.makeText(ViewDataActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Handle delete button
        delete.setOnClickListener(v -> {
            deleteWorkouts(dataToBeDeleted);
            updateList(listOfWorkouts,spin.getSelectedItem().toString());
            dataToBeDeleted.clear();
        });

    }

    private void updateList(ListView l, String s){
        if (s.equals("All")){
            s = "";
        }
        ArrayList<WorkoutData> converter = db.getWorkouts(s);

        //converter implicitly calls toString method from WorkoutData
        ArrayAdapter<WorkoutData> WOAdapter = new ArrayAdapter<>(ViewDataActivity.this, android.R.layout.simple_list_item_1, converter);
        l.setAdapter(WOAdapter);
    }

    private void deleteWorkouts(ArrayList<Integer> al){
        if (al.isEmpty()) {
            Toast.makeText(ViewDataActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int val : al){
            db.deleteWorkout(val);
        }
    }


}