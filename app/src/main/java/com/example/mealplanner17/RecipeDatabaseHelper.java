package com.example.mealplanner17;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipe_data.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the 'recipes' table
        String createTableSQL = "CREATE TABLE recipes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "meal_name TEXT," +
                "ingredients TEXT," +
                "cooking_instructions TEXT)";
        db.execSQL(createTableSQL);
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table and recreate it
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }
}
