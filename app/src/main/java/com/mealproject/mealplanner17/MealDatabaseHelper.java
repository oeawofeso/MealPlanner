package com.mealproject.mealplanner17;

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
    public static final String TABLE_LUNCH = "lunch"; // Added lunch table
    public static final String TABLE_DINNER = "dinner";

    // Database creation SQL statements for each table
    private static final String DATABASE_CREATE_BREAKFAST = "CREATE TABLE "
            + TABLE_BREAKFAST + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MEAL_NAME
            + " TEXT NOT NULL, " + COLUMN_INGREDIENTS + " TEXT NOT NULL, "
            + COLUMN_COOKING_INSTRUCTIONS + " TEXT NOT NULL);";

    private static final String DATABASE_CREATE_LUNCH = "CREATE TABLE "
            + TABLE_LUNCH + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MEAL_NAME
            + " TEXT NOT NULL, " + COLUMN_INGREDIENTS + " TEXT NOT NULL, "
            + COLUMN_COOKING_INSTRUCTIONS + " TEXT NOT NULL);"; // Added lunch table creation SQL

    private static final String DATABASE_CREATE_DINNER = "CREATE TABLE "
            + TABLE_DINNER + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MEAL_NAME
            + " TEXT NOT NULL, " + COLUMN_INGREDIENTS + " TEXT NOT NULL, "
            + COLUMN_COOKING_INSTRUCTIONS + " TEXT NOT NULL);";

    public MealDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_BREAKFAST);
        database.execSQL(DATABASE_CREATE_LUNCH); // Execute SQL for creating lunch table
        database.execSQL(DATABASE_CREATE_DINNER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic, if needed
    }

    // Method to get all lunch meals from the database
    public Cursor getAllLunchMeals() {
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
