package com.vnkmpn.database;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class GetRecipeImage extends AsyncTask<String, String, String> {
	
	// Progress Dialog
    private ProgressDialog pDialog;
    private FragmentActivity fa;
    private String mUrl;
    Bitmap bm = null;
	
	public GetRecipeImage (FragmentActivity parent, String imageUrl) { 
		   fa = parent;
		   mUrl = imageUrl;
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
	   protected String doInBackground(String... args) {
		   int success = 0; 
		   try {
			   URL aURL = new URL(mUrl);
	           URLConnection conn = aURL.openConnection();
	           conn.connect();
	           InputStream is = conn.getInputStream();
	           BufferedInputStream bis = new BufferedInputStream(is);
	           bm = BitmapFactory.decodeStream(bis);
	           bis.close();
	           is.close();
	           success = 1;
	       } catch (IOException e) {
	           Log.e("getRecipeImage", "Error getting bitmap", e);
	       }		   
		   
	       if (success == 0) {
	    	   Log.d("getRecipeImage", "json request failed - required field likely missing");
	       }
	      
	       return null;
	   }
	   
	   public Bitmap getImage() {
	    	return bm;
	    }
	   
	   /**
	    * After completing background task Dismiss the progress dialog
	    * **/
	   protected void onPostExecute(String file_url) {
	       // dismiss the dialog after getting all products
		   pDialog.dismiss();	       
	   }
}
