package com.mealproject.mealplanner17.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This method implements retrofit and set the API url to use for fetch data
 * https://api.spoonacular.com/recipes/
 */
public class ApiCallForRecpies {
    private static final String BASE_URL = " https://api.spoonacular.com/recipes/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
