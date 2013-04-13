package com.vnkmpn.recipes;



import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;

import com.vnkmpn.database.CreateRecipe;
import com.vnkmpn.database.Recipe;
import com.vnkmpn.database.UpdateRecipe;
import com.vnkmpn.nrecipes.R;

@TargetApi(12)
public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "_id";
    public static final String ARG_ITEM_NAME = "_name";
    public static final String ARG_ITEM_INGREDS = "_ingredients";
    
    int id = 0;
    String name = "nameeroni";
    String ingreds = "ingreds";  
    
    public FragmentActivity fa ;
    public Activity a;
    private ButtonClickListener bcl;
    private boolean newRecipe = false;

    Recipe recipe = null;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        fa = this.getActivity();
        
        bcl = new ButtonClickListener(fa, this);
                
        id = getArguments().getInt(ARG_ITEM_ID, id);
        if (id == 0) {
        	newRecipe = true;
        }

        name = getArguments().getString(ARG_ITEM_NAME, name);

        ingreds = getArguments().getString(ARG_ITEM_INGREDS, ingreds);
        recipe = new Recipe();
        recipe.setID(id);
        recipe.setName(name);
        recipe.setIngredient(ingreds);
        if (id != 0) {
        	Log.d("RecipeDetailFragment", "Recipe info: " + Integer.toString(id) + name + ingreds);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_recipe_detail_readwrite, container, false);
        if (recipe != null) {
        	((EditText) rootView.findViewById(R.id.editName)).setText(recipe.getName());
        	((EditText) rootView.findViewById(R.id.editIngredients)).setText(recipe.getIngredient());
        	final Button srb = (Button) rootView.findViewById(R.id.saveRecipeButton);
        	srb.setOnClickListener(bcl);
        }
        return rootView;
    }
    
    
    public class ButtonClickListener implements OnClickListener, OnTouchListener{	
    	UpdateRecipe updateRecipeTask;
    	CreateRecipe createRecipeTask;

    	@SuppressWarnings("unused")
    	private final String TAG = "Main: BCL";
    	FragmentActivity mParent;
    	Fragment mParentFragment;
    	
    	private String mName, mIngredients, mId;
    	    	
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
    		
    		View myView = mParentFragment.getActivity().findViewById(android.R.id.content);
    		
    		EditText recipeName =  (EditText) myView.findViewById(R.id.editName);
        	EditText recipeIngredients = (EditText) myView.findViewById(R.id.editIngredients);
        	
        	if (!newRecipe) {    		
	    		setParameters(Integer.toString(recipe.getID()), recipeName.getText().toString(), recipeIngredients.getText().toString());
	    		updateRecipeTask = new UpdateRecipe(mParent, mParentFragment, mId, mName, mIngredients);
	    		updateRecipeTask.execute();
	    		getFragmentManager().popBackStackImmediate();
        	} else {
        		createRecipeTask = new CreateRecipe(mParent, mParentFragment, recipeName.getText().toString(), recipeIngredients.getText().toString());
        		createRecipeTask.execute();
	    		getFragmentManager().popBackStackImmediate();
        	}
    	}
    	
    	public void setParameters(String id, String name, String ingredients){
    		mId = id;
    		mName = name;
    		mIngredients = ingredients;		
    	}

    }
    
    
    
}
