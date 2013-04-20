package com.vnkmpn.database;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;
import android.util.Log;

public class UploadRecipeImage extends AsyncTask<String, String, String> {

        
   private File mRecipeImageFile;
   
   
   public UploadRecipeImage (String recipeImageFilePath) { 
	   mRecipeImageFile = new File(recipeImageFilePath);
   }

   /**
    * getting All products from url
    * */
   protected String doInBackground(String... args) {
	   int success = 0; 
	   
	   FTPClient ftpClient = new FTPClient();
	    try {
	    	Log.d("ftpclient", "saving picture: " + mRecipeImageFile.getAbsolutePath());
	        ftpClient.connect(InetAddress.getByName("ftp.vnkmpn.com"));
	        ftpClient.login("recipe_images@vnkmpn.com", "recipes");
	        
	        Log.d("ftpclient", "reply string: " + ftpClient.getReplyString());

	        if (ftpClient.getReplyString().contains("230")) {
	            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
	            BufferedInputStream buffIn = null;
	            buffIn = new BufferedInputStream(new FileInputStream(mRecipeImageFile.getAbsolutePath()));
	            ftpClient.enterLocalPassiveMode();
	            boolean result = ftpClient.storeFile(mRecipeImageFile.getName(), buffIn);
	            buffIn.close();
	            if (result) {
	            	success = 1;
	            }
	            ftpClient.logout();
	            ftpClient.disconnect();
	        }

	    } catch (SocketException e) {
	        Log.e("ftpclient", "SocketException");
	        e.printStackTrace();
	    } catch (UnknownHostException e) {
	        Log.e("ftpclient", "Unknown host exception ");
	        e.printStackTrace();
	    } catch (IOException e) {
	        Log.e("ftpclient", "IOException ");
	        e.printStackTrace();
	    }
	   
       if (success == 0) {
    	   Log.d("ftpclient", "json request failed - required field likely missing");
       }
      
       return null;
   }
   
   /**
    * After completing background task Dismiss the progress dialog
    * **/
   protected void onPostExecute(String file_url) {
       // dismiss the dialog after getting all products
       
   }

}