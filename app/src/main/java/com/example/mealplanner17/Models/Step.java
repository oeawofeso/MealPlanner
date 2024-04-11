package com.example.mealplanner17.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("step")
    @Expose
    private String step;


    public Step(Integer number, String step) {
        this.number = number;
        this.step = step;
    }
    public Integer getNumber() {
        return number;
    }
    public String getStep() {
        return step;
    }




}
