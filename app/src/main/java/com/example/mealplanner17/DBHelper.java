package com.example.mealplanner17; // Replace with your actual package name


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // Constructor and other methods here
    public DBHelper(Context context) {
        super(context, "userinputdata.db", null, 1);
    }

    // Method to insert dinner data into the database
    public void insertDinner(String foodName, String ingredients, String cookingInstructions) {
        // Get a writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the data to be inserted
        ContentValues values = new ContentValues();
        values.put("food_name", foodName);
        values.put("ingredients", ingredients);
        values.put("cooking_instructions", cookingInstructions);

        // Execute the INSERT statement
        long newRowId = db.insert("dinner_table", null, values);

        // Check if insertion was successful
        if (newRowId != -1) {
            Log.d("DBHelper", "Dinner data inserted successfully!");
        } else {
            Log.e("DBHelper", "Error inserting dinner data into database!");
        }

        // Close the database connection
        db.close();
    }

    // Method to retrieve all dinner data from the database
    public Cursor getAllDinnerData() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                "food_name",
                "ingredients",
                "cooking_instructions"
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = null;
        String[] selectionArgs = null;

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;

        Cursor cursor = db.query(
                "dinner_table",         // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Create the 'dinner_table' table
        String createTableSQL = "CREATE TABLE dinner_table (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "food_name TEXT," +
                "ingredients TEXT," +
                "cooking_instructions TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing 'dinner_table' table and recreate it
        db.execSQL("DROP TABLE IF EXISTS dinner_table");
        onCreate(db);
    }
    public void deleteDinner(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("dinner_table", "_id = ?", new String[]{String.valueOf(id)});
        if (deletedRows > 0) {
            Log.d("DBHelper", "Dinner data deleted successfully!");
        } else {
            Log.e("DBHelper", "Error deleting dinner data from database!");
        }
        db.close();
    }


}
