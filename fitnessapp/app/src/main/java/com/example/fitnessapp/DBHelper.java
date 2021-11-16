package com.example.fitnessapp;

//Import packages needed for SQLite database


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    //App will only have 1 database
    private static final String DB_NAME = "workouts.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        //Create database (happens once)
        //to-do -> Make two tables, one with workout data, one with list of workout types.
        //Make sure workout data table takes input from page, can use FK but probably not necessary for this example
        //Make sure workout types table can talk to spinner (shouldn't be anything special for this part)
        String createWOTable = "CREATE TABLE WORKOUT (ID INTEGER PRIMARY KEY AUTOINCREMENT, WOTYPE TEXT, REPS INTEGER, WEIGHT INTEGER)";
        String createTypesTable = "CREATE TABLE TYPES (ID INTEGER PRIMARY KEY AUTOINCREMENT, WOTYPE TEXT)";

        db.execSQL(createWOTable);
        db.execSQL(createTypesTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DB will not be upgraded for this example, nothing needs to be done.
    }

    public boolean addWorkout(WorkoutData wo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("WOTYPE", format(wo.getType()));
        cv.put("REPS",wo.getReps());
        cv.put("WEIGHT",wo.getWeight());

        long ret = db.insert("WORKOUT",null, cv);
        db.close();

        //Returns -1 if an error occurs.
        return ret != -1;
    }


    public boolean addType(String type){

        //Call getTypes to see if type already exists.
        if(getTypes(type).size() != 0){
            //Type already in database -> add functionality to let them know at some point.
            return false;
        }

        type = format(type);

        //Now we know this is a unique type.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("WOTYPE", type);

        long ret = db.insert("TYPES",null, cv);
        db.close();

        //Returns -1 if an error occurs.
        return ret != -1;
    }

    //Expects already formatted string
    public ArrayList<WorkoutData> getWorkouts(String type){
        //If type = "", assume all workouts requested.
        ArrayList<WorkoutData> values = new ArrayList<>();



        String statement = "SELECT * FROM WORKOUT";

        //Add more specificity if needed
        if(!type.equals("")) {
            type = format(type);
            statement += (" WHERE WOTYPE='"+type+"'");
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(statement, null);

        if(c.moveToFirst()){
            do{
                //Put values into WorkoutData object
                WorkoutData temp = new WorkoutData(c.getString(1),c.getInt(2),c.getInt(3), c.getInt(0));
                values.add(temp);

            } while(c.moveToNext());
        }

        c.close();
        db.close();

        return values;
    }

    //Again, "" assumes all types.
    public ArrayList<String> getTypes(String type){
        ArrayList<String> values = new ArrayList<>();

        String statement = "SELECT * FROM TYPES";

        //Add more specificity if needed
        if(!type.equals("")) {
            type = format(type);
            statement += (" WHERE WOTYPE='"+type+"'");

        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(statement, null);

        if(c.moveToFirst()){
            do{
                //cursor 0 is ID -> don't need it
                //Index 1 Holds the type I need
                values.add(c.getString(1));

            } while(c.moveToNext());
        }

        c.close();
        db.close();

        return values;
    }

    public boolean deleteWorkout(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        int val = db.delete("WORKOUT", "ID =" + Integer.toString(id), null);
        db.close();

        return val > 0;
    }

    //Should use a specific type of workout
    //Returns null if no workout found
    public WorkoutData getLast(String type){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM WORKOUT WHERE WOTYPE='"+type+"'", null);
        WorkoutData data;

        if(c.moveToLast()){
            data = new WorkoutData(c.getString(1),c.getInt(2),c.getInt(3), c.getInt(0));
        } else {
            data = null;
        }
        c.close();
        db.close();
        return data;
    }

    String format(String s){
        s = s.trim();
        s = s.toUpperCase();
        return s;
    }
}
