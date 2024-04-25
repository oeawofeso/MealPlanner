package com.mealproject.mealplanner17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "userinputdata.db", null, 1);
    }

    public void insertDinner(String foodName, String ingredients, String cookingInstructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_name", foodName);
        values.put("ingredients", ingredients);
        values.put("cooking_instructions", cookingInstructions);
        long newRowId = db.insert("dinner_table", null, values);
        if (newRowId != -1) {
            Log.d("DBHelper", "Dinner data inserted successfully!");
        } else {
            Log.e("DBHelper", "Error inserting dinner data into database!");
        }
        db.close();
    }

    public Cursor getAllDinnerDataWithIndex() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "_id",
                "food_name",
                "ingredients",
                "cooking_instructions"
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor cursor = db.query(
                "dinner_table",
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
        String createTableSQL = "CREATE TABLE dinner_table (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "food_name TEXT," +
                "ingredients TEXT," +
                "cooking_instructions TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dinner_table");
        onCreate(db);
    }

    // Method to delete a dinner meal from the database by index
    public void deleteDinnerMealByIndex(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM dinner_table LIMIT 1 OFFSET ?", new String[]{String.valueOf(index - 1)});
        long id = -1;
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndexOrThrow("_id");
            id = cursor.getLong(idColumnIndex);
        }
        cursor.close();

        if (id != -1) {
            db.delete("dinner_table", "_id = ?", new String[]{String.valueOf(id)});
            Log.d("DBHelper", "Dinner data deleted successfully!");
        } else {
            Log.e("DBHelper", "Error deleting dinner data from database!");
        }
        db.close();
    }
}
