package com.example.iiatimd_android.Fragments;

import java.math.BigDecimal;

public class Cocktail {

    private String title;
    private String desc;
    private Integer calories;
    private BigDecimal percentage;

    public Cocktail(String title, String desc, Integer calories, BigDecimal percentage){
        this.title = title;
        this.desc = desc;
        this.calories = calories;
        this.percentage = percentage;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDesc(){
        return this.desc;
    }

    public Integer getCalories(){
        return this.calories;
    }

    public BigDecimal getPercentage(){
        return this.percentage;
    }

}


