package com.vnkmpn.recipes;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vnkmpn.database.CreateRecipe;
import com.vnkmpn.database.ReadRecipe;
import com.vnkmpn.database.Recipe;
import com.vnkmpn.database.UpdateRecipe;
import com.vnkmpn.database.UploadRecipeImage;

@TargetApi(12)
public class EditRecipeFragment extends Fragment {

    public static final String ARG_ITEM_ID = "_id";
    public static final String ARG_ITEM_NAME = "_name";
    public static final String ARG_ITEM_INGREDS = "_ingredients";
    public static final String ARG_ITEM_OVEN = "_ovenTemp";
    public static final String ARG_ITEM_COOK = "_cookTime";
    public static final String ARG_ITEM_FROM = "_from";
    public static final String ARG_ITEM_DIRECTIONS = "_directions";
    
    int id = 0;
    String ovenTemp = "default_500";  
    String name = "default_name";
    String ingredients = "default_ingredientss";
    String directions = "default_cook";  
    String from = "default_mom";  
    String cookTime = "default_minutes";  
    
    public FragmentActivity fa ;
    public Activity a;
    private SaveButtonClickListener sbcl;
    private TakePhotoButtonClickListener tpbcl;
    private ViewPhotoButtonClickListener vpbcl;
    private boolean newRecipe = false;

    Recipe recipe = null;
    ReadRecipe rr;
    
    File f = null;

    public EditRecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        fa = this.getActivity();
        
        sbcl = new SaveButtonClickListener(fa, this);
        tpbcl = new TakePhotoButtonClickListener();
                
        id = getArguments().getInt(ARG_ITEM_ID, id);
        vpbcl = new ViewPhotoButtonClickListener(fa, id);
        
    	rr = new ReadRecipe(fa, this, Integer.toString(id));
    	rr.execute();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	if (id == 0 ) {
        	newRecipe = true;
        	recipe = new Recipe(id, name, ingredients, ovenTemp, cookTime, from, directions);
        } else { 
        	if (rr == null) {
	        	rr = new ReadRecipe(fa, this, Integer.toString(id));
	        	rr.execute();
        	}
        }
    	refreshView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_recipe_detail_readwrite, container, false);
        if (recipe != null) {
        	refreshView();
        }
        return rootView;
    }
    
    public void refreshView() {
    	recipe = rr.getRecipe();
    	View rootView = getView();
    	if (rootView != null) {
	    	if (recipe != null) {
		    	((EditText) rootView.findViewById(R.id.editName)).setText(recipe.getName());
		    	((EditText) rootView.findViewById(R.id.editIngredients)).setText(recipe.getIngredients());
		    	((EditText) rootView.findViewById(R.id.editFrom)).setText(recipe.getFrom());
		    	((EditText) rootView.findViewById(R.id.editCookTime)).setText(recipe.getCookTime());
		    	((EditText) rootView.findViewById(R.id.editDirections)).setText(recipe.getDirections());
		    	((EditText) rootView.findViewById(R.id.editOvenTemp)).setText(recipe.getOvenTemp());
	    	}
	    	final Button srb = (Button) rootView.findViewById(R.id.saveRecipeButton);
	    	srb.setOnClickListener(sbcl);
	    	final Button tpb = (Button) rootView.findViewById(R.id.addPictureButton);
	    	tpb.setOnClickListener(tpbcl);
        	final Button vpb = (Button) rootView.findViewById(R.id.viewImage);
        	vpb.setOnClickListener(vpbcl);
    	}
    }
    
    public class SaveButtonClickListener implements OnClickListener, OnTouchListener{	
    	UpdateRecipe updateRecipeTask;
    	CreateRecipe createRecipeTask;

    	@SuppressWarnings("unused")
    	private final String TAG = "Main: BCL";
    	FragmentActivity mParent;
    	Fragment mParentFragment;
    	
    	public SaveButtonClickListener(FragmentActivity parent, Fragment parentFragment){
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
    		
    		View myView = mParentFragment.getActivity().findViewById(android.R.id.content);
    		if (recipe == null) {
    			recipe = new Recipe();
    		}    		
    		recipe.setName(((EditText) myView.findViewById(R.id.editName)).getText().toString());
    		recipe.setIngredients(((EditText) myView.findViewById(R.id.editIngredients)).getText().toString());
    		recipe.setFrom(((EditText) myView.findViewById(R.id.editFrom)).getText().toString());
    		recipe.setCookTime(((EditText) myView.findViewById(R.id.editOvenTemp)).getText().toString());
    		recipe.setOvenTemp(((EditText) myView.findViewById(R.id.editCookTime)).getText().toString());
    		recipe.setDirections(((EditText) myView.findViewById(R.id.editDirections)).getText().toString());
        	
        	if (!newRecipe) {    		
	    		updateRecipeTask = new UpdateRecipe(mParent, mParentFragment, recipe);
	    		updateRecipeTask.execute();
        	} else {
        		createRecipeTask = new CreateRecipe(mParent, mParentFragment, recipe);
        		createRecipeTask.execute();
        	}
        	Log.d("editRecipe", "popping back up a level");
    		getFragmentManager().popBackStackImmediate();
    	}
    }
    
    public class TakePhotoButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int actionCode = 0;
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Log.d("editpict", "setting up image intent");
			try {
				f = createImageFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			Log.d("editpict", "starting image intent");
		    startActivityForResult(takePictureIntent, actionCode);		    
		}
		
		@SuppressLint("SimpleDateFormat")
		private File createImageFile() throws IOException {
		    // Create an image file name
		    //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		    String imageFileName = "/recipes/" + Integer.toString(recipe.getID()) + ".jpg";
		    File recipeImagePath = new File(Environment.getExternalStoragePublicDirectory(
    	            Environment.DIRECTORY_PICTURES) + "/recipes/");
		    recipeImagePath.mkdirs();
		    File image = new File(
		    	    Environment.getExternalStoragePublicDirectory(
		    	            Environment.DIRECTORY_PICTURES
		    	        ), 
		    	        imageFileName
		    	    );
		    Log.d("editpict", "saving picture to: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + imageFileName);
		    return image;
		}
		
	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d("EditPict", "activity returned result code: " + Integer.toString(resultCode));
		
		String filePath = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES
        ) + "/recipes/" + Integer.toString(id) + ".jpg";
		Log.d("editpict", "uploading image: " + filePath);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(fa);
        String ftp_password = prefs.getString(SettingsFragment.KEY_PREF_FTPPW, "");
		new UploadRecipeImage(filePath, ftp_password).execute();
		
	}
    
}
