package com.vnkmpn.recipes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vnkmpn.database.CreateRecipe;
import com.vnkmpn.database.ReadRecipe;
import com.vnkmpn.database.Recipe;
import com.vnkmpn.database.UpdateRecipe;

@TargetApi(12)
public class ViewRecipeFragment extends Fragment {

	public static final String ARG_ITEM_ID = "_id";
	public static final String ARG_ITEM_NAME = "_name";
	public static final String ARG_ITEM_INGREDS = "_ingredients";
	public static final String ARG_ITEM_OVEN = "_ovenTemp";
	public static final String ARG_ITEM_COOK = "_cookTime";
	public static final String ARG_ITEM_FROM = "_from";
	public static final String ARG_ITEM_DIRECTIONS = "_directions";
	public static final String ARG_ITEM_TWOPANE = "_twopane";

	int id = 0;
	String ovenTemp = "default_500";  
	String name = "default_name";
	String ingredients = "default_ingredientss";
	String directions = "default_cook";  
	String from = "default_mom";  
	String cookTime = "default_minutes";  
	private boolean mTwoPane;

	public FragmentActivity fa ;
	public Activity a;
	private ButtonClickListener bcl;
	private ViewPhotoButtonClickListener vpbcl;

	Recipe recipe = null;
	ReadRecipe rr;

	public ViewRecipeFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		fa = this.getActivity();
		bcl = new ButtonClickListener(fa, this);
		id = getArguments().getInt(ARG_ITEM_ID, id);
		mTwoPane = getArguments().getBoolean(ARG_ITEM_TWOPANE, mTwoPane);
		vpbcl = new ViewPhotoButtonClickListener(fa, id, mTwoPane);   
	}

	@Override
	public void onResume() {
		super.onResume();
		rr = new ReadRecipe(fa, this, Integer.toString(id));
		rr.execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recipe_detail_readonly, container, false);
		if (recipe != null) {
			((TextView) rootView.findViewById(R.id.viewName)).setText(recipe.getName());
			((TextView) rootView.findViewById(R.id.viewIngredients)).setText(recipe.getIngredients());
			((TextView) rootView.findViewById(R.id.viewFrom)).setText(recipe.getFrom());
			((TextView) rootView.findViewById(R.id.viewCookTime)).setText(recipe.getCookTime());
			((TextView) rootView.findViewById(R.id.viewDirections)).setText(recipe.getDirections());
			((TextView) rootView.findViewById(R.id.viewOvenTemp)).setText(recipe.getOvenTemp());
			final Button erb = (Button) rootView.findViewById(R.id.editRecipeButton);
			erb.setOnClickListener(bcl);
			final Button vpb = (Button) rootView.findViewById(R.id.viewImage2);
			vpb.setOnClickListener(vpbcl);

		}
		return rootView;
	}

	public void refreshView() {
		recipe = rr.getRecipe();    	


		if (id != 0) {
			Log.d("RecipeViewFragment", "Recipe id:" + Integer.toString(id) + 
					" name: " + recipe.getName() + 
					" from: " + recipe.getFrom() +
					" cooktime: " + recipe.getCookTime() +
					" oventemp: " + recipe.getOvenTemp() +
					" ingredients: " + recipe.getIngredients() +
					" directions: " + recipe.getDirections());
		} else {
			startEditActivity(fa, recipe);
		}

		View rootView = this.getView();
		if (recipe != null) {
			((TextView) rootView.findViewById(R.id.viewName)).setText(recipe.getName());
			((TextView) rootView.findViewById(R.id.viewIngredients)).setText(recipe.getIngredients());
			((TextView) rootView.findViewById(R.id.viewFrom)).setText(recipe.getFrom());
			((TextView) rootView.findViewById(R.id.viewCookTime)).setText(recipe.getCookTime());
			((TextView) rootView.findViewById(R.id.viewDirections)).setText(recipe.getDirections());
			((TextView) rootView.findViewById(R.id.viewOvenTemp)).setText(recipe.getOvenTemp());
			final Button erb = (Button) rootView.findViewById(R.id.editRecipeButton);
			erb.setOnClickListener(bcl);
			final Button vpb = (Button) rootView.findViewById(R.id.viewImage2);
			vpb.setOnClickListener(vpbcl);
		}
	}

	public class ButtonClickListener implements OnClickListener, OnTouchListener{	
		UpdateRecipe updateRecipeTask;
		CreateRecipe createRecipeTask;

		@SuppressWarnings("unused")
		private final String TAG = "Main: BCL";
		FragmentActivity mParent;
		Fragment mParentFragment;

		public ButtonClickListener(FragmentActivity parent, Fragment parentFragment){
			mParent = parent;
			mParentFragment = parentFragment;

		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onClick(View v) {
			startEditActivity(mParent, recipe);
		}

	}

	private void startEditActivity(Activity parent, Recipe recipe) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			if (recipe != null) {
			arguments.putInt(EditRecipeFragment.ARG_ITEM_ID, recipe.getID());
			arguments.putString(EditRecipeFragment.ARG_ITEM_NAME, recipe.getName());
			arguments.putString(EditRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
			arguments.putString(EditRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
			arguments.putString(EditRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
			arguments.putString(EditRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
			arguments.putString(EditRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
			} else {
				arguments.putInt(EditRecipeFragment.ARG_ITEM_ID, 0);
			}
			arguments.putBoolean(EditRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
			EditRecipeFragment fragment = new EditRecipeFragment();
			fragment.setArguments(arguments);
			fa.getSupportFragmentManager().beginTransaction()
			.replace(R.id.recipe_detail_container, fragment)
			.commit();
		} else {
			Intent editIntent = new Intent(parent, EditRecipeActivity.class);
			if (recipe != null) {
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_ID, recipe.getID());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_NAME, recipe.getName());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
			} else {
				editIntent.putExtra(EditRecipeFragment.ARG_ITEM_ID, 0);
			}
			editIntent.putExtra(EditRecipeFragment.ARG_ITEM_TWOPANE, mTwoPane);
			startActivity(editIntent);
		}

	}



}
