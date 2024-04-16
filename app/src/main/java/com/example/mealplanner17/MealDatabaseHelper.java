package com.example.mealplanner17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MealDatabaseHelper extends SQLiteOpenHelper {
    // Define constants for database name, version, and table names
    private static final String DATABASE_NAME = "mealplanner.db";
    private static final int DATABASE_VERSION = 1;

    // Define columns for all tables
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEAL_NAME = "meal_name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_COOKING_INSTRUCTIONS = "cooking_instructions";

    // Table names for breakfast, lunch, and dinner
    public static final String TABLE_BREAKFAST = "breakfast";
    public static final String TABLE_LUNCH = "lunch";
    public static final String TABLE_DINNER = "dinner";

    // Database creation SQL statements for each table
    private static final String DATABASE_CREATE_BREAKFAST = "create table "
            + TABLE_BREAKFAST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_MEAL_NAME
            + " text not null, " + COLUMN_INGREDIENTS + " text not null, "
            + COLUMN_COOKING_INSTRUCTIONS + " text not null);";

    private static final String DATABASE_CREATE_LUNCH = "create table "
            + TABLE_LUNCH + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_MEAL_NAME
            + " text not null, " + COLUMN_INGREDIENTS + " text not null, "
            + COLUMN_COOKING_INSTRUCTIONS + " text not null);";

    private static final String DATABASE_CREATE_DINNER = "create table "
            + TABLE_DINNER + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_MEAL_NAME
            + " text not null, " + COLUMN_INGREDIENTS + " text not null, "
            + COLUMN_COOKING_INSTRUCTIONS + " text not null);";

    public MealDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_BREAKFAST);
        database.execSQL(DATABASE_CREATE_LUNCH);
        database.execSQL(DATABASE_CREATE_DINNER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic, if needed
    }

    // Method to get all dinner meals from the database
    public Cursor getAllDinnerMeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_DINNER, null, null, null, null, null, null);
    }

    // Method to delete a breakfast meal from the database
    public void deleteBreakfastMeal(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BREAKFAST, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Similarly, you can add methods for lunch meals
    // getAllLunchMeals(), deleteLunchMeal()
}
