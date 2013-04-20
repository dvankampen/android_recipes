package com.vnkmpn.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vnkmpn.recipes.EditRecipeFragment;
import com.vnkmpn.recipes.ViewRecipeFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ReadRecipe extends AsyncTask<String, String, String> {
	
	 // Progress Dialog
    private ProgressDialog pDialog;
 // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ViewRecipeFragment mVrf;
    private EditRecipeFragment mErf;
    private FragmentActivity mFa;
    // url to get all products list
    private static String url_read_recipe = "http://vnkmpn.com/android/recipe_read.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RECIPE = "recipe";
    private static final String TAG_NAME = "name";
    private static final String TAG_INGREDIENTS = "ingredients";
	private static final String TAG_ID = "id";
	private static final String TAG_FROM = "from";
	private static final String TAG_OVENTEMP = "oventemp";
	private static final String TAG_COOKTIME = "cooktime";
	private static final String TAG_DIRECTIONS = "directions";
	private static final String TAG_SERVES = "serves";
	private static final String TAG_IMAGEURL = "imageurl";
    
    // recipes JSONArray
    JSONArray recipes = null;
    
    ArrayList<Recipe> recipesList = new ArrayList<Recipe>();
    private String mID;
    Recipe mRecipe = null;
    
    public ReadRecipe (FragmentActivity fa, ViewRecipeFragment vrf, String id){ 
    	mFa = fa;
    	mVrf = vrf;
    	mID = id;
    }
    
    public ReadRecipe (FragmentActivity fa, EditRecipeFragment erf, String id){ 
    	mFa = fa;
    	mErf = erf;
    	mID = id;
    }
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mFa);
        pDialog.setMessage("Loading recipe. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
	    params.add(new BasicNameValuePair(TAG_ID, mID));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_read_recipe, "GET", params);

        // Check your log cat for JSON response
        Log.d("Read recipe: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                recipes = json.getJSONArray(TAG_RECIPE);

                // looping through All Products
                for (int i = 0; i < recipes.length(); i++) {
                    JSONObject c = recipes.getJSONObject(i);

                    // Storing each json item in variable
                    int id = c.getInt(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String ingredients = c.getString(TAG_INGREDIENTS);
                    String from = c.getString(TAG_FROM);
                    String cookTime = c.getString(TAG_COOKTIME);
                    String ovenTemp = c.getString(TAG_OVENTEMP);
                    String directions = c.getString(TAG_DIRECTIONS);
                    String serves = c.getString(TAG_SERVES);
                    String imageUrl = c.getString(TAG_IMAGEURL);

                    Recipe recipe = new Recipe();
                    recipe.setID(id);
                    recipe.setName(name);
                    recipe.setIngredients(ingredients);
                    recipe.setFrom(from);
                    recipe.setCookTime(cookTime);
                    recipe.setOvenTemp(ovenTemp);
                    recipe.setDirections(directions);
                    recipe.setServes(serves);
                    recipe.setImageURL(imageUrl);

                    // adding HashList to ArrayList
                    mRecipe = recipe;
                }
            } else {
                // no products found
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Recipe getRecipe() {
    	return mRecipe;
    }


    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
    	try {
        pDialog.dismiss();
    	} catch (IllegalArgumentException iae) {
    		
    	}
        if (mVrf != null) {
        	mVrf.refreshView();
        } else if (mErf != null) {
        	mErf.refreshView();
        }
    }

}