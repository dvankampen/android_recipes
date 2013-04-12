package com.vnkmpn.nrecipes;



import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;

import com.vnkmpn.database.Recipe;

@TargetApi(12)
public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "_id";
    public static final String ARG_ITEM_NAME = "_name";
    public static final String ARG_ITEM_INGREDS = "_ingredients";
    
    int id = 0;
    String name = "nameeroni";
    String ingreds = "ingreds";  
    
    public FragmentActivity fa ;
    private ButtonClickListener bcl;

    Recipe recipe = null;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        fa = this.getActivity();
        
        bcl = new ButtonClickListener(fa, this);
                
        id = getArguments().getInt(ARG_ITEM_ID, id);

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
        	bcl.setParameters(Integer.toString(recipe.getID()), recipe.getName(), recipe.getIngredient());
        	srb.setOnClickListener(bcl);
        }
        return rootView;
    }
    
    
    
}
