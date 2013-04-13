package com.vnkmpn.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.support.v4.app.ListFragment;

public class LoadAllRecipes extends AsyncTask<String, String, String> {
	
	 // Progress Dialog
    private ProgressDialog pDialog;
 // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private FragmentActivity fa;
    private android.support.v4.app.ListFragment mFrag;
    // url to get all products list
    private static String url_all_recipes = "http://vnkmpn.com/android/recipe_get_all.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RECIPES = "recipes";
    private static final String TAG_NAME = "name";
    private static final String TAG_INGREDIENTS = "ingredients";
	private static final String TAG_ID = "id";
    
    // recipes JSONArray
    JSONArray recipes = null;
    
    ArrayList<Recipe> recipesList = new ArrayList<Recipe>();
    
    public LoadAllRecipes (FragmentActivity parent, ListFragment listFragment){ 
    
    fa = parent;
    mFrag = listFragment;
}
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(fa);
        pDialog.setMessage("Loading recipes. Please wait...");
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
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_recipes, "GET", params);

        // Check your log cat for JSON response
        Log.d("Got all recipes: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                recipes = json.getJSONArray(TAG_RECIPES);

                // looping through All Products
                for (int i = 0; i < recipes.length(); i++) {
                    JSONObject c = recipes.getJSONObject(i);

                    // Storing each json item in variable
                    int id = c.getInt(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String ingredients = c.getString(TAG_INGREDIENTS);

                    Recipe recipe = new Recipe();
                    recipe.setID(id);
                    recipe.setName(name);
                    recipe.setIngredient(ingredients);

                    // adding HashList to ArrayList
                    recipesList.add(recipe);
                }
            } else {
                // no products found
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
        // updating UI from Background Thread
        fa.runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed JSON data into ListView
                 * */
            	mFrag.setListAdapter(new ArrayAdapter<Recipe>(fa,
                        android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1,
                        recipesList));
                
            }
        });

    }

}