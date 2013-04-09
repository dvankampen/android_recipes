package com.vnkmpn.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class RecipesDBAdapter {
	
	private final Context context;
 
    // All Static variables 
    // Recipes table name
    private static final String TABLE_RECIPES = "_recipes";
 
    // Recipes Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "_name";
    public static final String KEY_INGRED = "_ingredients";
    
    private static DatabaseHelper mDbHelper = null;

    private static SQLiteDatabase mDb = null;
    
    public RecipesDBAdapter(Context context) {
        this.context = context;
    }
    
    private int connect() throws SQLException {
    	
    	Connection conn = null;
    	try {
    		String driver = "com.mysql.jdbc.Driver"; //"net.sourceforge.jtds.jdbc.Driver";
    		Class.forName(driver).newInstance();
    		String connString = "jdbc:jtds:sqlserver://69.195.126.101:3306/vnkmpnco_recipes;encrypt=false;user=vnkmpnco_author;password=recipe;instance=SQLEXPRESS;";
    		String username = "vnkmcpnco_author";
    		String password = "recipe";
    		conn = DriverManager.getConnection(connString,username,password);
    		Log.w("Connection","open");
    		Statement stmt = conn.createStatement();
    		ResultSet reset = stmt.executeQuery("select * from TableName");
    		 
    		//Print the data to the console
    		while(reset.next()){
    		Log.w("Data:",reset.getString(3));
//    		              Log.w("Data",reset.getString(2));
    		}
    		conn.close();
    		 
    		} catch (Exception e)
    		{
    		Log.w("Error connection","" + e.getMessage());
    		}
		
    	
    	return 0;    	
    }
    
    public RecipesDBAdapter open() throws SQLException {
    		Log.d("recipesDBAdapter", "opening database");
    		mDbHelper = DatabaseHelper.getInstance(context);
    		mDb = mDbHelper.getWritableDatabase();
    		
    		connect();
	    		
    		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_INGRED + " TEXT" + ")";
    		try {
    		mDb.execSQL(CREATE_CONTACTS_TABLE);
    		} catch (SQLiteException e) {
    			Log.d("recipesDBAdapter", "SQLiteException: " + e.getLocalizedMessage());
    		}
    	return this;
    }
 
    public void close() {
    	  if (mDbHelper != null) {
    		  mDbHelper.close();
    	  }
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    public // Adding new recipe
    void addRecipe(Recipe recipe) {
    	Log.d("recipesDBAdapter", "adding recipe " + recipe.getName());
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName()); // Recipe Name
        values.put(KEY_INGRED, recipe.getIngredient()); // Recipe Phone
 
        // Inserting Row
        if (mDb != null) {
        	mDb.insert(TABLE_RECIPES, null, values);
        } else {
        	Log.i("recipesDBAdapter", "database null when adding recipe");
        }

        Log.d("recipesDBAdapter", "added recipe " + recipe.getName());
    }
 
    // Getting single recipe
    public Cursor getRecipeByID(int id) {
 
        Cursor cursor = mDb.query(TABLE_RECIPES, new String[] { KEY_ID,
                KEY_NAME, KEY_INGRED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        //Recipe recipe = new Recipe(Integer.parseInt(cursor.getString(0)),
                //cursor.getString(1), cursor.getString(2));
        return cursor;
    }
 
    // Getting All Recipes
    public Cursor getAllRecipes() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;

        if (mDb == null) {
        	Log.d("recipesDBAdapter", "database is null in getAllRecipes");
        }
        Cursor cursor = mDb.rawQuery(selectQuery, null);
         // return recipe list
        return cursor;
    }
    
    // Getting All Recipes
    public List<Recipe> getAllRecipes1() {
        List<Recipe> contactList = new ArrayList<Recipe>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;
        
        if (mDb != null) {
        	
        	Cursor cursor = null;

        	try {
        		cursor = mDb.rawQuery(selectQuery, null);
        	} catch (SQLiteException e) {
        		Log.d("recipesDBAdapter", "SQLiteException :" + e.getLocalizedMessage());
        		return null;
        	}
 
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                Recipe recipe = new Recipe();
	                recipe.setID(Integer.parseInt(cursor.getString(0)));
	                recipe.setName(cursor.getString(1));
	                recipe.setIngredient(cursor.getString(2));
	                // Adding recipe to list
	                contactList.add(recipe);
	            } while (cursor.moveToNext());
	        }
        }
        return contactList;
    }
 
    // Updating single recipe
    public int updateRecipe(Recipe recipe) {
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_INGRED, recipe.getIngredient());
 
        // updating row
        return mDb.update(TABLE_RECIPES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(recipe.getID()) });
    }
 
    // Deleting single recipe
    public void deleteRecipe(Recipe recipe) {
    	Log.d("recipesDBAdapter", "deleted recipe " + recipe.getName());
    	mDb.delete(TABLE_RECIPES, KEY_ID + " = ?",
                new String[] { String.valueOf(recipe.getID()) });
    }
 
    // Getting contacts Count
    public int getRecipesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPES;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
}
