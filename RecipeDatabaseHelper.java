package com.example.mealplanner17;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecipeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mealplanner.db";
    private static final int DATABASE_VERSION = 1;


    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the 'recipes' table
        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY," +
                "food_name TEXT," +
                "ingredients TEXT," +
                "instructions TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement if you want to handle database upgrades
        // This method is called when the database needs to be upgraded.
        // You can drop the existing table and create a new one, or alter the table.
        // For simplicity, we'll just drop the table and recreate it.
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }
}

