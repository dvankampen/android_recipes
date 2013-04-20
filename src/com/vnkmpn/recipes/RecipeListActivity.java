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
			openEditRecipe(null);
			break;
		case R.id.settings_menu:
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			break;
		}
		return true;
	}

	@Override
	public void onItemSelected(Recipe recipe) {
		openViewRecipe(recipe);
	}
	private void openEditRecipe(Recipe recipe) {
		if(recipe == null) {
			recipe = new Recipe();
			recipe.setID(0);
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
		arguments.putInt(EditRecipeFragment.ARG_ITEM_ID, recipe.getID());
		arguments.putString(EditRecipeFragment.ARG_ITEM_NAME, recipe.getName());
		arguments.putString(EditRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
		arguments.putString(EditRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
		arguments.putString(EditRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
		arguments.putString(EditRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
		arguments.putString(EditRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
		arguments.putBoolean(EditRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
		EditRecipeFragment fragment = new EditRecipeFragment();
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
		Intent editIntent = new Intent(this, EditRecipeActivity.class);
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_ID, recipe.getID());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_NAME, recipe.getName());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
		editIntent.putExtra(EditRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
		startActivity(editIntent);
	}
	}

	private void openViewRecipe(Recipe recipe) {
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
			arguments.putBoolean(ViewRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
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
			Intent viewIntent = new Intent(this, ViewRecipeActivity.class);
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_ID, recipe.getID());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_NAME, recipe.getName());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
			viewIntent.putExtra(ViewRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
			startActivity(viewIntent);
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
				new DeleteRecipe(Integer.toString(recipe.getID())).execute();
				//refresh the current view
				finish();
				startActivity(RecipeListActivity.this.getIntent());   
			}

		})
		.setNegativeButton("No", null)
		.show();



	}
}
