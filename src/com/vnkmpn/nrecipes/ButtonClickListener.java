package com.vnkmpn.nrecipes;

import com.vnkmpn.database.UpdateRecipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class ButtonClickListener implements OnClickListener, OnTouchListener{	
	UpdateRecipe updateRecipeTask;

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
		updateRecipeTask = new UpdateRecipe(mParent, mParentFragment, mId, mName, mIngredients);
		updateRecipeTask.execute();		
	}
	
	public void setParameters(String id, String name, String ingredients){
		mId = id;
		mName = name;
		mIngredients = ingredients;		
	}

}
