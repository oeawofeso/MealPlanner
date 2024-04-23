package com.example.mealplanner17.LunchActivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LunchMealDatabaseHelper extends SQLiteOpenHelper {
    // Define constants for database name, version, and table names
    private static final String DATABASE_NAME = "lunchmealplanner.db";
    private static final int DATABASE_VERSION = 1;

    // Define columns for lunch table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEAL_NAME = "meal_name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_COOKING_INSTRUCTIONS = "cooking_instructions";

    // Table name for lunch
    public static final String TABLE_LUNCH = "lunch";

    // Database creation SQL statement for the lunch table
    private static final String DATABASE_CREATE_LUNCH = "CREATE TABLE "
            + TABLE_LUNCH + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MEAL_NAME
            + " TEXT NOT NULL, " + COLUMN_INGREDIENTS + " TEXT NOT NULL, "
            + COLUMN_COOKING_INSTRUCTIONS + " TEXT NOT NULL);";

    public LunchMealDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the lunch table
        db.execSQL(DATABASE_CREATE_LUNCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic, if needed
    }

    // Method to insert a lunch meal into the database
    public void insertLunch(String mealName, String ingredients, String cookingInstructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEAL_NAME, mealName);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_COOKING_INSTRUCTIONS, cookingInstructions);
        db.insert(TABLE_LUNCH, null, values);
        db.close();
    }

    // Method to retrieve all lunch data from the database
    public Cursor getAllLunchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_LUNCH, null, null, null, null, null, null);
    }

    // Method to delete a lunch meal from the database by ID
    public void deleteLunchMealById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LUNCH, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Method to delete a lunch meal from the database by index
    public void deleteLunchMealByIndex(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_LUNCH + " LIMIT 1 OFFSET ?", new String[]{String.valueOf(index)});
        long id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }
        cursor.close();

        if (id != -1) {
            db.delete(TABLE_LUNCH, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }
        db.close();
    }
}
