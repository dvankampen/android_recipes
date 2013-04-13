package com.vnkmpn.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class DeleteRecipe extends AsyncTask<String, String, String> {
	

	private final String TAG = "CreateRecipe";
	
	 // Progress Dialog
   private ProgressDialog pDialog;
   //Creating JSON Parser object
   JSONParser jParser;
   private FragmentActivity fa;
   // url to get all products list
   private static String url_delete_recipe = "http://vnkmpn.com/android/recipe_delete.php";

   // JSON Node names
   private static final String TAG_SUCCESS = "success";
   private static final String TAG_ID = "id";
      
   private String mID;
   
   ArrayList<Recipe> recipesList = new ArrayList<Recipe>();
   
   public DeleteRecipe (FragmentActivity parent, String id){ 
	   fa = parent;
	   mID = id;
	   jParser = new JSONParser();
   }
	 
   /**
    * Before starting background thread Show Progress Dialog
    * */
   @Override
   protected void onPreExecute() {
       super.onPreExecute();
       pDialog = new ProgressDialog(fa);
       pDialog.setMessage("Deleting recipe Please wait...");
       pDialog.setIndeterminate(false);
       pDialog.setCancelable(false);
       pDialog.show();
   }

   /**
    * getting All products from url
    * */
   protected String doInBackground(String... args) {
	   int success;
	   try {
		   //Building Parameters
	       List<NameValuePair> params = new ArrayList<NameValuePair>();
	              
	       params.add(new BasicNameValuePair(TAG_ID, mID));
	       
	       Log.d(TAG, "Deleting recipe w/ id: " + mID);
	              
	       JSONObject json = jParser.makeHttpRequest(url_delete_recipe, "POST", params);
	       
	       // Check your log cat for JSON response
	       Log.d(TAG, "vnkmpn.com response: " + json.toString());
	       
	       // json success tag
	       success = json.getInt(TAG_SUCCESS);
	       if (success != 1) {
	    	   Log.d(TAG, "json request failed - required field likely missing");
	       }
       } catch (JSONException e) {
           e.printStackTrace();
       }

       return null;
   }
   
   /**
    * After completing background task Dismiss the progress dialog
    * **/
   protected void onPostExecute(String file_url) {
       // dismiss the dialog after getting all products
       pDialog.dismiss();
   }

}