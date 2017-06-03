package com.jogoler.jogolmaps;

/**
 * Created by Arief Wijaya on 06-Oct-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class LocationRepo {
    private DataBaseHelper dbHelper;

    public LocationRepo(Context context) {
        dbHelper = new DataBaseHelper(context);

        try {

            dbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
    }


    public int insert(Location location) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Location.KEY_longitude, location.longitude);
        values.put(Location.KEY_latitude, location.latitude);
        values.put(Location.KEY_desc,location.desc);
        values.put(Location.KEY_name, location.name);

        // Inserting Row
        long location_ID = db.insert(Location.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) location_ID;
    }
    public Cursor  getLocationList() {
        //Open connection to read only
        try {

            dbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
//        SQLiteDatabase db = dbHelper.openDataBase();
        String selectQuery =  "SELECT  rowid as " +
                Location.KEY_ROWID + "," +
                Location.KEY_ID + "," +
                Location.KEY_name + "," +
                Location.KEY_desc + "," +
                Location.KEY_longitude + "," +
                Location.KEY_latitude +
                " FROM " + Location.TABLE;


        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = dbHelper.getQuery(selectQuery);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    public Cursor  getLocationListByKeyword(String search) {
        //Open connection to read only
        try {

            dbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }

       // SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                Location.KEY_ROWID + "," +
                Location.KEY_ID + "," +
                Location.KEY_name + "," +
                Location.KEY_desc + "," +
                Location.KEY_latitude + "," +
                Location.KEY_longitude +
                " FROM " + Location.TABLE +
                " WHERE " +  Location.KEY_name + "  LIKE  '%" +search + "%' "
                ;


        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = dbHelper.getQuery(selectQuery);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    public Location getLocationById(int Id){
        //Open connection to read only
        try {

            dbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Location.KEY_ID + "," +
                Location.KEY_name + "," +
                Location.KEY_desc + "," +
                Location.KEY_latitude + "," +
                Location.KEY_longitude +
                " FROM " + Location.TABLE
                + " WHERE " +
                Location.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Location location = new Location();

        //Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );
        Cursor cursor = dbHelper.getQuerywithString(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                location.location_ID =cursor.getInt(cursor.getColumnIndex(Location.KEY_ID));
                location.name =cursor.getString(cursor.getColumnIndex(Location.KEY_name));
                location.desc  =cursor.getString(cursor.getColumnIndex(Location.KEY_desc));
                location.longitude =cursor.getDouble(cursor.getColumnIndex(Location.KEY_longitude));
                location.latitude =cursor.getDouble(cursor.getColumnIndex(Location.KEY_latitude));

            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();
        return location;
    }




}
