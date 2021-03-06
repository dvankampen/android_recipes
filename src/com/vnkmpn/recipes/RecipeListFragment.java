package com.vnkmpn.recipes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.vnkmpn.database.LoadAllRecipes;
import com.vnkmpn.database.Recipe;

public class RecipeListFragment extends ListFragment {
	

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    public FragmentActivity fa ;
    public RecipeListActivity ra;

    public interface Callbacks {

        public void onItemSelected(Recipe recipe);

		public void onItemLongSelected(Recipe recipe);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Recipe recipe) {
        }
        public void onItemLongSelected(Recipe recipe) {
        	
        }
    };

    public RecipeListFragment() {
    }
    
    @Override
    public void onActivityCreated(Bundle savedState) {
    	super.onActivityCreated(savedState);
    	getListView().setLongClickable(true);
    	getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> listView, View view, int position, long id) {
    			
				Recipe recipe =  (Recipe) listView.getItemAtPosition(position);
				mCallbacks.onItemLongSelected(recipe);
				return true;
			}});
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fa = this.getActivity();
        ra = (RecipeListActivity) fa;
 
        // Loading products in Background Thread
        new LoadAllRecipes(fa, (ListFragment)(this)).execute();        
    }
    
    @Override
    public void onResume() {
    	super.onResume();
        // Loading products in Background Thread
        new LoadAllRecipes(fa, (ListFragment)(this)).execute();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        //Get the cursor, positioned to the corresponding row in the result set
        Recipe recipe =  (Recipe) listView.getItemAtPosition(position);
        mCallbacks.onItemSelected(recipe);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }   
}
