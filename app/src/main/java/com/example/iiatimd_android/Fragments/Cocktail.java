package com.example.iiatimd_android.Fragments;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cocktail {

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String desc;

    @ColumnInfo
    private String calories;

    @ColumnInfo
    private String percentage;

    @PrimaryKey
    @NonNull
    private int id;

    public Cocktail(String title, String desc, String calories, String percentage, int id){
        this.title = title;
        this.desc = desc;
        this.calories = calories;
        this.percentage = percentage;
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDesc(){
        return this.desc;
    }

    public String getCalories(){
        return this.calories;
    }

    public String getPercentage(){
        return this.percentage;
    }

    public int getId(){
        return this.id;
    }

}


