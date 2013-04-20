package com.vnkmpn.recipes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

import com.vnkmpn.database.CreateRecipe;
import com.vnkmpn.database.GetRecipeImage;
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
    
    int id = 0;
    String ovenTemp = "default_500";  
    String name = "default_name";
    String ingredients = "default_ingredientss";
    String directions = "default_cook";  
    String from = "default_mom";  
    String cookTime = "default_minutes";  
    
    public FragmentActivity fa ;
    public Activity a;
    private ButtonClickListener bcl;

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
        	Bitmap recipePic = getImageBitmap(recipe.getImageURL());
	    	((ImageView) rootView.findViewById(R.id.viewRecipeImage)).setImageBitmap(recipePic);
        }
        return rootView;
    }
    
    public void refreshView() {
    	recipe = rr.getRecipe(); //new Recipe(id, name, ingredients, ovenTemp, cookTime, from, directions);
        
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
        	Bitmap recipePic = getImageBitmap(recipe.getImageURL());
	    	((ImageView) rootView.findViewById(R.id.viewRecipeImage)).setImageBitmap(recipePic);
        }
    }
    
    private Bitmap getImageBitmap(String url) {
        GetRecipeImage gri = (GetRecipeImage) new GetRecipeImage(fa, url).execute();
        Bitmap bm = gri.getImage();
        return bm;
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
    	Intent editIntent = new Intent(parent, EditRecipeActivity.class);
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_ID, recipe.getID());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_NAME, recipe.getName());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_INGREDS, recipe.getIngredients());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_FROM, recipe.getFrom());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_DIRECTIONS, recipe.getDirections());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_COOK, recipe.getCookTime());
        editIntent.putExtra(ViewRecipeFragment.ARG_ITEM_OVEN, recipe.getOvenTemp());
        startActivity(editIntent);
    	
    }
    
    
    
}
