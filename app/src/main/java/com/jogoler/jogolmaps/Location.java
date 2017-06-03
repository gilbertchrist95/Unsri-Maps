package com.jogoler.jogolmaps;

/**
 * Created by Arief Wijaya on 06-Oct-16.
 */

public class Location {
    // Labels table name
    public static final String TABLE = "Location";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "_id";
    public static final String KEY_name = "name";
    public static final String KEY_desc = "desc";
    public static final String KEY_longitude = "longitude";
    public static final String KEY_latitude = "latitude";

    // property help us to keep data
    public int location_ID;
    public String name;
    public String desc;
    public double longitude;
    public double latitude;
}
