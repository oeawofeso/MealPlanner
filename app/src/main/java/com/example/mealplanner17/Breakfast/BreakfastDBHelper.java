package com.example.mealplanner17.Breakfast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BreakfastDBHelper extends SQLiteOpenHelper {

    public BreakfastDBHelper(Context context) {
        super(context, "userinputdatabreakfast.db", null, 1);
    }

    public void insertBreakfastData(String mealName, String ingredients, String cookingInstructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("meal_name", mealName);
        values.put("ingredients", ingredients);
        values.put("cooking_instructions", cookingInstructions);
        long newRowId = db.insert("breakfast_table", null, values);
        if (newRowId != -1) {
            Log.d("BreakfastDBHelper", "Breakfast data inserted successfully!");
        } else {
            Log.e("BreakfastDBHelper", "Error inserting breakfast data into database!");
        }
        db.close();
    }

    public Cursor getAllBreakfastDataWithIndex() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "_id",
                "meal_name",
                "ingredients",
                "cooking_instructions"
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor cursor = db.query(
                "breakfast_table",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE breakfast_table (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "meal_name TEXT," +
                "ingredients TEXT," +
                "cooking_instructions TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS breakfast_table");
        onCreate(db);
    }

    // Method to delete a breakfast meal from the database by index
    public void deleteBreakfastMealByIndex(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM breakfast_table LIMIT 1 OFFSET ?", new String[]{String.valueOf(index - 1)});
        long id = -1;
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndexOrThrow("_id");
            id = cursor.getLong(idColumnIndex);
        }
        cursor.close();

        if (id != -1) {
            db.delete("breakfast_table", "_id = ?", new String[]{String.valueOf(id)});
            Log.d("BreakfastDBHelper", "Breakfast data deleted successfully!");
        } else {
            Log.e("BreakfastDBHelper", "Error deleting breakfast data from database!");
        }
        db.close();
    }
}
