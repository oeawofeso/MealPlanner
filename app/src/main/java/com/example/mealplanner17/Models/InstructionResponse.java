package com.example.mealplanner17.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionResponse {
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;


    public List<Step> getSteps() {

        return steps;
    }


}
