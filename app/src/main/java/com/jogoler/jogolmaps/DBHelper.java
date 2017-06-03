package com.jogoler.jogolmaps;

/**
 * Created by Arief Wijaya on 06-Oct-16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/com.jogoler.jogolmaps/databases/";
    // Database Name
    private static final String DATABASE_NAME = "jogolmaps.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_LOCATION = "CREATE TABLE " + Location.TABLE  + "("
                + Location.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Location.KEY_name + " TEXT, "
                + Location.KEY_longitude + " REAL, "
                + Location.KEY_latitude + " REAL, "
                + Location.KEY_desc + " TEXT )";

        db.execSQL(CREATE_TABLE_LOCATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Location.TABLE);

        // Create tables again
        onCreate(db);

    }

}
