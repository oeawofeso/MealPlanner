package com.mealproject.mealplanner17;

import java.util.HashSet;
import java.util.Set;

public class ClickedRecipeIdsSingleton {
    private static ClickedRecipeIdsSingleton instance;
    private Set<String> clickedRecipeIds = new HashSet<>();

    private ClickedRecipeIdsSingleton() {

    }

    public static synchronized ClickedRecipeIdsSingleton getInstance() {
        if (instance == null) {
            instance = new ClickedRecipeIdsSingleton();
        }
        return instance;
    }

    public Set<String> getClickedRecipeIds() {
        return clickedRecipeIds;
    }

    public void setClickedRecipeIds(Set<String> clickedRecipeIds) {

        this.clickedRecipeIds.addAll(clickedRecipeIds); // Update with the new set
    }
}
