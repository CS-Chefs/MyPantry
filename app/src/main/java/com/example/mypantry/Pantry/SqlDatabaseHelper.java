package com.example.mypantry.Pantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// This Class handles of all the SQL statements and sets up the CRUD functionality of the database

public class SqlDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "listDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PANTRY = "pantryitems";
    private static final String KEY_PANTRY_ID = "_id";
    private static final String KEY_PANTRY_ITEM_NAME = "item";
    private static final String KEY_PANTRY_ITEM_DETAILS = "details";



    public SqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Specifies what values every item in the database should have
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PANTRY_TABLE =
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        KEY_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                        KEY_PANTRY_ITEM_NAME + " TEXT," +
                        KEY_PANTRY_ITEM_DETAILS + " TEXT" +
                ")";
        db.execSQL(CREATE_PANTRY_TABLE);
    }

    // CHECKS IF DB EXISTS AND DROPS TABLE IF NECESSARY
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
            onCreate(db);
        }
    }

    // ADD ITEM TO DATABASE
    public long addDb(pantryItem pantryItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PANTRY_ITEM_NAME, pantryItem.getName());
        contentValues.put(KEY_PANTRY_ITEM_DETAILS, pantryItem.getDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_PANTRY,null,contentValues);
        db.close();
        return id;
    }


    // DELETE ITEM FROM DATABASE
    public boolean deleteDb(String name){
        boolean result = false;
        String query = "Select * FROM " + TABLE_PANTRY + " where " + KEY_PANTRY_ITEM_NAME + " LIKE '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        pantryItem pantryItem = new pantryItem();

        if (cursor.moveToFirst()) {
            pantryItem.setId(cursor.getInt(0));
            db.delete(TABLE_PANTRY, KEY_PANTRY_ID + " = ?",
                    new String[] { String.valueOf(pantryItem.getId()) });
            cursor.close();
            result = true;
        }
        db.close();

        return result;
    }


    // RETRIEVE SINGLE ITEM FROM DATABASE
    public pantryItem getPantryItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, new String[]{KEY_PANTRY_ID,
                        KEY_PANTRY_ITEM_NAME, KEY_PANTRY_ITEM_DETAILS}, KEY_PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        pantryItem item = new pantryItem(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return item;
    }


    // GET ALL ITEMS FROM DATABASE
    public List<pantryItem> getAllItems() {
        List<pantryItem> itemList = new ArrayList<pantryItem>();
        String selectQuery = "SELECT * FROM " + TABLE_PANTRY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        pantryItem pantryItem;
        if (cursor.moveToFirst()) {
            do {
                pantryItem = new pantryItem();
                pantryItem.setId(cursor.getInt(0));
                pantryItem.setName(cursor.getString(1));
                pantryItem.setDetails(cursor.getString(2));
                itemList.add(pantryItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }


    // UPDATE ITEM IN DATABASE
    public void updateItem(pantryItem pantryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = KEY_PANTRY_ID +"=" + ((Long) pantryItem.getId()).intValue();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_PANTRY_ITEM_NAME, pantryItem.getName());
        contentValues.put(KEY_PANTRY_ITEM_DETAILS, pantryItem.getDetails());

        int ret = db.update(TABLE_PANTRY, contentValues, strFilter, null);
        db.close();
    }

}


