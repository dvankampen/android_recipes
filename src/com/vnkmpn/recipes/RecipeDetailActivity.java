package com.vnkmpn.recipes;

import com.vnkmpn.nrecipes.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

public class RecipeDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            int id = getIntent().getIntExtra(RecipeDetailFragment.ARG_ITEM_ID, 0);
            String name = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_NAME);
            String ingreds = getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_INGREDS);
            arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, id);
            arguments.putString(RecipeDetailFragment.ARG_ITEM_NAME, name);
            arguments.putString(RecipeDetailFragment.ARG_ITEM_INGREDS, ingreds);
            RecipeDetailFragment fragment = new RecipeDetailFragment();

            Log.d("RecipeDetailActivity", "OnCreate - item " + id);
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
