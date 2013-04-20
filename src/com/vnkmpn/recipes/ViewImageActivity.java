package com.vnkmpn.recipes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ViewImageActivity extends FragmentActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        
        if (savedInstanceState == null) {
        	Bundle arguments = new Bundle();
        	int id = getIntent().getIntExtra(ViewImageFragment.ARG_ITEM_ID, 0);
        	arguments.putInt(ViewRecipeFragment.ARG_ITEM_ID, id);
        	ViewImageFragment fragment = new ViewImageFragment();
        	fragment.setArguments(arguments);
        	getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
	}
}
