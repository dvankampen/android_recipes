package com.vnkmpn.recipes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vnkmpn.database.DeleteRecipe;
import com.vnkmpn.database.Recipe;

public class RecipeListActivity extends FragmentActivity
        implements RecipeListFragment.Callbacks {

    private boolean mTwoPane;
        
    @SuppressWarnings("unused")
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.recipe_list_menu, menu);
        menu.findItem(R.id.create_recipe).setIcon(android.R.drawable.ic_menu_add);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
        case R.id.create_recipe:
        	openDetailsWindow(null);
        	break;
        }
        return true;
    }

    @Override
    public void onItemSelected(Recipe recipe) {
        openDetailsWindow(recipe);
    }
    
    private void openDetailsWindow(Recipe recipe) {
    	if (recipe == null) {
    		recipe = new Recipe();
    		recipe.setID(0);
    		recipe.setName("Insert name here");
    		recipe.setIngredients("Insert Ingredients here");
    	}
    	if (mTwoPane) {
            Bundle arguments = new Bundle();
            Log.d("RecipeListActivity", "twopane Bundling arguments id: " + recipe.getID() +
            		", name: " + recipe.getName() + 
            		", ingredients: " + recipe.getIngredients() +
            		", from: " + recipe.getFrom() +
            		", oventemp: " + recipe.getOvenTemp() +
            		", directions: " + recipe.getDirections() +
            		", cooktime: " + recipe.getCookTime());
            arguments.putInt(ViewRecipeFragment.ARG_ITEM_ID, recipe.getID());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_NAME, recipe.getName());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
            arguments.putString(ViewRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
            ViewRecipeFragment fragment = new ViewRecipeFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();

        } else {
        	Log.d("RecipeListActivity", "Bundling arguments id: " + recipe.getID() +
            		", name: " + recipe.getName() + 
            		", ingredients: " + recipe.getIngredients() +
            		", from: " + recipe.getFrom() +
            		", oventemp: " + recipe.getOvenTemp() +
            		", directions: " + recipe.getDirections() +
            		", cooktime: " + recipe.getCookTime());
            Intent detailIntent = new Intent(this, ViewRecipeActivity.class);
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_ID, recipe.getID());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_NAME, recipe.getName());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
            detailIntent.putExtra(ViewRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
            startActivity(detailIntent);
        }
    }


    @Override
	public void onItemLongSelected(final Recipe recipe) {
    	
    	new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_menu_delete)
        .setTitle("Deleting Recipe " + recipe.getName())
        .setMessage("Are you sure you want to delete this recipe?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	new DeleteRecipe(RecipeListActivity.this, Integer.toString(recipe.getID())).execute();
    		//refresh the current view
    		finish();
    		startActivity(RecipeListActivity.this.getIntent());   
        }

    })
    .setNegativeButton("No", null)
    .show();

		
		
	}
}
