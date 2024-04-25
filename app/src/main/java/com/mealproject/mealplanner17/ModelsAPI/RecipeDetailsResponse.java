package com.mealproject.mealplanner17.ModelsAPI;

import java.util.ArrayList;

public class RecipeDetailsResponse {
    public int id;
    public String title;
    public String image;
    public String sourceName;

    public ArrayList<ExtendedIngredient> extendedIngredients;


    public int servings;
}
