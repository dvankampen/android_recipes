package com.vnkmpn.recipes;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnkmpn.database.GetRecipeImage;

@TargetApi(12)
public class ViewImageFragment extends Fragment {
	
	public static final String ARG_ITEM_ID = "_id";
	public static final String ARG_ITEM_TWOPANE = "_twopane";
	Drawable recipeImage;
	int mId;
	public FragmentActivity fa ;
	GetRecipeImage gri;
	
	public ViewImageFragment() {
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt(ARG_ITEM_ID, mId);  
        fa = this.getActivity();
        gri = (GetRecipeImage) new GetRecipeImage(fa,this, mId);
    	gri.execute();        
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.recipe_image_viewer, container, false);
    	
    	return rootView;
	}
	
	

	

}
