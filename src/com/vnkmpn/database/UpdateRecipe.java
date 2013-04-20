package com.vnkmpn.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class UpdateRecipe extends AsyncTask<String, String, String> {


	private final String TAG = "UpdateRecipe";

	// Progress Dialog
	private ProgressDialog pDialog;
	//Creating JSON Parser object
	JSONParser jParser;
	private FragmentActivity fa;
	// url to get all products list
	private static String url_update_recipe = "http://vnkmpn.com/android/recipe_update.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NAME = "name";
	private static final String TAG_INGREDIENTS = "ingredients";
	private static final String TAG_FROM = "from";
	private static final String TAG_OVENTEMP = "oventemp";
	private static final String TAG_COOKTIME = "cooktime";
	private static final String TAG_DIRECTIONS = "directions";
	private static final String TAG_ID = "id";

	private Recipe mRecipe;

	ArrayList<Recipe> recipesList = new ArrayList<Recipe>();

	public UpdateRecipe (FragmentActivity parent, Fragment fragment, Recipe recipe) { 
		fa = parent;
		mRecipe = recipe;
		jParser = new JSONParser();
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(fa);
		pDialog.setMessage("Saving recipe Please wait...");
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

			params.add(new BasicNameValuePair(TAG_NAME, mRecipe.getName()));
			params.add(new BasicNameValuePair(TAG_INGREDIENTS, mRecipe.getIngredients()));
			params.add(new BasicNameValuePair(TAG_ID, Integer.toString(mRecipe.getID())));
			params.add(new BasicNameValuePair(TAG_FROM, mRecipe.getFrom()));
			params.add(new BasicNameValuePair(TAG_COOKTIME, mRecipe.getCookTime()));
			params.add(new BasicNameValuePair(TAG_OVENTEMP, mRecipe.getOvenTemp()));
			params.add(new BasicNameValuePair(TAG_DIRECTIONS, mRecipe.getDirections()));

			JSONObject json = jParser.makeHttpRequest(url_update_recipe, "POST", params);

			// Check your log cat for JSON response
			Log.d(TAG, "vnkmpn.com response: " + json.toString());

			// json success tag
			success = json.getInt(TAG_SUCCESS);
			if (success == 1) {
				// product successfully deleted
				// notify previous activity by sending code 100
				Intent i = fa.getIntent();
				// send result code 100 to notify about product deletion
				fa.setResult(100, i);
				fa.finish();
			} else {
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