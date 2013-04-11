package com.vnkmpn.nrecipes;



import com.vnkmpn.database.Recipe;
import com.vnkmpn.database.RecipesDBAdapter;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(12)
public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "_id";
    public static final String ARG_ITEM_NAME = "_name";
    public static final String ARG_ITEM_INGREDS = "_ingredients";
    
    int id = 0;
    String name = "nameeroni";
    String ingreds = "ingreds";    

    Recipe recipe = null;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
        //View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    	View rootView = inflater.inflate(R.layout.fragment_recipe_detail_readwrite, container, false);
        if (recipe != null) {
            //((TextView) rootView.findViewById(R.id.recipe_detail)).setText(recipe.getName() + " and " + recipe.getIngredient());
        	((EditText) rootView.findViewById(R.id.editName)).setText(recipe.getName());
        	((EditText) rootView.findViewById(R.id.editIngredients)).setText(recipe.getIngredient());
        }
        return rootView;
    }
}
