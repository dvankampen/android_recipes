package com.vnkmpn.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class ViewRecipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            int id = getIntent().getIntExtra(ViewRecipeFragment.ARG_ITEM_ID, 0);
            String name = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_NAME);
            String ingreds = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_INGREDS);
            String from = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_FROM);
            String ovenTemp = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_OVEN);
            String directions = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_DIRECTIONS);
            String cookTime = getIntent().getStringExtra(ViewRecipeFragment.ARG_ITEM_COOK);
            arguments.putInt(ViewRecipeFragment.ARG_ITEM_ID, id);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_NAME, name);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_INGREDS, ingreds);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_FROM, from);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_DIRECTIONS, directions);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_COOK, cookTime);
            arguments.putString(ViewRecipeFragment.ARG_ITEM_OVEN, ovenTemp);
            ViewRecipeFragment fragment = new ViewRecipeFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
