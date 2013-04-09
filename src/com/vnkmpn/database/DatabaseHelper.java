package com.vnkmpn.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "recipeManager";
    
 // Recipes table name
    private static final String TABLE_RECIPES = "_recipes";
 
    // Recipes Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "_name";
    public static final String KEY_INGRED = "_ingredients";
	
	private static DatabaseHelper mInstance = null;
	
	public static DatabaseHelper getInstance(Context ctx) {
	      
	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (mInstance == null) {
	      mInstance = new DatabaseHelper(ctx.getApplicationContext());
	    }
	    return mInstance;
	  }
	
	private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		Log.d("DatabaseHelper", "deleting existing DB");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
 
        // Create tables again
        onCreate(db);		
	}
	
}