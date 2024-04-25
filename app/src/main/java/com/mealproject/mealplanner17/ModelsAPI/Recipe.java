package com.mealproject.mealplanner17.ModelsAPI;

public class Recipe {


    private final String sourceName;
    public int aggregateLikes;

    public int id;
    public String title;

    public int servings;

    public String image;

    public boolean isFavorite;

    public Recipe(int id, String title, String image, int servings, String sourceName) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.servings = servings;
        this.sourceName = sourceName;
    }

}
