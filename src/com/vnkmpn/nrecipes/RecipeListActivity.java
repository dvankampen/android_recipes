package com.vnkmpn.nrecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.vnkmpn.database.JSONParser;
import com.vnkmpn.database.Recipe;
import com.vnkmpn.database.RecipesDBAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class RecipeListActivity extends FragmentActivity
        implements RecipeListFragment.Callbacks {

    private boolean mTwoPane;
    
    public RecipesDBAdapter db = new RecipesDBAdapter(this);
    
    private FragmentActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);  
        
        instance = RecipeListActivity.this;
        
        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
            ((RecipeListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.recipe_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Recipe recipe) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            Log.d("RecipeListActivity", "Bundling arguments id: " + recipe.getID() + ", name: " + recipe.getName() + ", ingredients: " + recipe.getIngredient());
            arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, recipe.getID());
            arguments.putString(RecipeDetailFragment.ARG_ITEM_NAME, recipe.getName());
            arguments.putString(RecipeDetailFragment.ARG_ITEM_INGREDS, recipe.getIngredient());
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();

        } else {
        	Log.d("RecipeListActivity", "Bundling arguments id: " + recipe.getID() + ", name: " + recipe.getName() + ", ingredients: " + recipe.getIngredient());
            Intent detailIntent = new Intent(this, RecipeDetailActivity.class);
            detailIntent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, recipe.getID());
            detailIntent.putExtra(RecipeDetailFragment.ARG_ITEM_NAME, recipe.getName());
            detailIntent.putExtra(RecipeDetailFragment.ARG_ITEM_INGREDS, recipe.getIngredient());
            startActivity(detailIntent);
        }
    }
    
    public RecipesDBAdapter getDB() {
    	return db;
    }
}
