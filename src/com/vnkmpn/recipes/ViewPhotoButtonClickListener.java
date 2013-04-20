package com.vnkmpn.recipes;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

public class ViewPhotoButtonClickListener implements OnClickListener, OnTouchListener {
	FragmentActivity mParent;
	int mId;
	public ViewPhotoButtonClickListener(FragmentActivity parent, int id){
		mParent = parent;
		mId = id;
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent editIntent = new Intent(mParent, ViewImageActivity.class);
        editIntent.putExtra(ViewImageFragment.ARG_ITEM_ID, mId);
    	mParent.startActivity(editIntent);
		
	}
	
	

}
