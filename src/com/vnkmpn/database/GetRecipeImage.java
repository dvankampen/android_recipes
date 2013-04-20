package com.vnkmpn.database;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class GetRecipeImage extends AsyncTask<String, String, String> {
	
	// Progress Dialog
    private ProgressDialog pDialog;
    private FragmentActivity fa;
    private int mId;
    Drawable recipeImage = null;
	
	public GetRecipeImage (FragmentActivity parent, int recipeID) { 
		   fa = parent;
		   mId = recipeID;
	   }
	
	/**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(fa);
        pDialog.setMessage("Loading Image. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

	   /**
	    * getting All products from url
	    * */
	   @SuppressWarnings("deprecation")
	protected String doInBackground(String... args) {
		   int success = 0; 
		   String pathName = Environment.getExternalStoragePublicDirectory(
		            Environment.DIRECTORY_PICTURES
	        ) + "/recipes/" + Integer.toString(mId) + ".jpg";
		   File picFile = new File(pathName);
		   if (!picFile.exists()) {
			   try {
				   URL aURL = new URL("http://vnkmpn.com/recipe_images/" + mId + ".jpg");
		           URLConnection conn = aURL.openConnection();
		           conn.connect();
		           InputStream is = conn.getInputStream();
		           BufferedInputStream bis = new BufferedInputStream(is);
		           Bitmap bm = BitmapFactory.decodeStream(bis);
		           bis.close();
		           is.close();
		           recipeImage = new BitmapDrawable(bm);
		           File recipeImageFile = new File(pathName);
		           FileOutputStream outStream = new FileOutputStream(recipeImageFile);
		           bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
		           outStream.flush();
		           outStream.close();
		           success = 1;
		       } catch (IOException e) {
		           Log.e("getRecipeImage", "Error getting bitmap", e);
		       }		   
			   
		       if (success == 0) {
		    	   Log.d("getRecipeImage", "json request failed - required field likely missing");
		       }
		   } else {
			   recipeImage = Drawable.createFromPath(pathName);
		   }
	      
	       return null;
	   }
	   
	   public Drawable getImage() {
	    	return recipeImage;
	    }
	   
	   /**
	    * After completing background task Dismiss the progress dialog
	    * **/
	   protected void onPostExecute(String file_url) {
	       // dismiss the dialog after getting all products
		   pDialog.dismiss();	       
	   }
}
