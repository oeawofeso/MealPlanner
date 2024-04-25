package com.example.mealplanner17.Breakfast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BreakfastMealDatabaseHelper extends SQLiteOpenHelper {

    // Define constants for database name, version, and table names
    private static final String DATABASE_NAME = "breaskfastmealplanner.db";
    private static final int DATABASE_VERSION = 1;

    // Define columns for all tables
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEAL_NAME = "meal_name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_COOKING_INSTRUCTIONS = "cooking_instructions";

    // Table name for breakfast
    public static final String TABLE_BREAKFAST = "breakfast";

    // Database creation SQL statement for breakfast table
    public static final String DATABASE_CREATE_BREAKFAST = "CREATE TABLE "
            + TABLE_BREAKFAST + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MEAL_NAME
            + " TEXT NOT NULL, " + COLUMN_INGREDIENTS + " TEXT NOT NULL, "
            + COLUMN_COOKING_INSTRUCTIONS + " TEXT NOT NULL);";

    public BreakfastMealDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_BREAKFAST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BREAKFAST);
        onCreate(db);
    }

    // Method to insert a new breakfast meal into the database
    public long insertBreakfastData(String mealName, String ingredients, String cookingInstructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEAL_NAME, mealName);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_COOKING_INSTRUCTIONS, cookingInstructions);
        long newRowId = db.insert(TABLE_BREAKFAST, null, values);
        db.close();
        return newRowId;
    }

    // Method to get all breakfast meals from the database
    public Cursor getAllBreakfastMeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BREAKFAST, null, null, null, null, null, null);
    }

    // Method to delete a breakfast meal from the database by ID
    public void deleteBreakfastMealById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BREAKFAST, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Method to delete a breakfast meal from the database by index
    public void deleteBreakfastMealByIndex(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_BREAKFAST + " LIMIT 1 OFFSET ?", new String[]{String.valueOf(index)});
        long id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }
        cursor.close();

        if (id != -1) {
            db.delete(TABLE_BREAKFAST, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }
        db.close();
    }
}
