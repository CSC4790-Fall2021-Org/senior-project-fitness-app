package com.example.fitnessapp;

public class WorkoutData {

    //Needs to store values from DB - ID, type, reps weight
    private int ID;
    private String type;
    private int reps;
    private int weight;

    //For passing into db
    public WorkoutData(String t, int r, int w){
        this.type = t;
        this.reps = r;
        this.weight = w;
        this.ID = -1;
    }

    //For getting info out of db
    public WorkoutData(String t, int r, int w, int id){
        this.type = t;
        this.reps = r;
        this.weight = w;
        this.ID = id;
    }

    //Make getters
    public int getID(){
        return this.ID;
    }

    public String getType(){
        return this.type;
    }

    public int getReps(){
        return this.reps;
    }

    public int getWeight(){
        return this.weight;
    }

    //Make new toString method
    public String toString(){
        return "Workout: " + type + "    Reps: " + reps + "   Weight: " + weight;
    }

}
