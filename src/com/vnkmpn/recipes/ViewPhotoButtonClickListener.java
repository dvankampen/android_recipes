package com.vnkmpn.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

public class ViewPhotoButtonClickListener implements OnClickListener, OnTouchListener {
	FragmentActivity mParent;
	int mId;
	boolean mTwoPane;
	public ViewPhotoButtonClickListener(FragmentActivity parent, int id, boolean twoPane){
		mParent = parent;
		mId = id;
		mTwoPane = twoPane;		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putBoolean(ViewImageFragment.ARG_ITEM_TWOPANE, mTwoPane);
			ViewImageFragment fragment = new ViewImageFragment();
			fragment.setArguments(arguments);
			mParent.getSupportFragmentManager().beginTransaction()
			.replace(R.id.recipe_detail_container, fragment)
			.commit();
		} else {
			Intent editIntent = new Intent(mParent, ViewImageActivity.class);
			editIntent.putExtra(ViewImageFragment.ARG_ITEM_ID, mId);
			mParent.startActivity(editIntent);
		}

	}



}
