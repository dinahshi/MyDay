package com.sehack1.myday;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ConfirmScreen extends Activity {
	
    private static final String TAG = "ConfirmScreen";
    private static final int SIDE_LENGTH = 350;

	private ImageView imageView;
	
	public String caption = "No caption chosen";
	public String date = "No date yet";
	public String imageInfo = "Default";
	public String filePath;
	
	EditText captionEditText;
	ImageButton post;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm);
		Log.d(TAG, "confirm created");
		
		// Compress and paint image
		Intent i = getIntent();
		filePath = i.getStringExtra(NewDayScreen.MESSAGE);		 
        Log.d(TAG, "Filepath retrieved: " + filePath);
		imageView.setImageBitmap(decodeSampledBitmapFromResource(filePath, SIDE_LENGTH, SIDE_LENGTH));
        
		addListenerOnButton();
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
	
		Log.d(TAG, "sample size: " + inSampleSize);
		return inSampleSize;
	}
	
	public String createMessage() {
		return filePath + "!#!#!#!#" + caption + "!#!#!#!#" + date;
	}
	public void saveMessage(View view) {
		String filename = "textfile.txt";
		String message = createMessage();
		try {
			FileOutputStream outputStream;
			outputStream = openFileOutput(filename, Context.MODE_APPEND);
			PrintStream ps = new PrintStream(outputStream);
			ps.print(message);
			ps.println();
			outputStream.close();
			ps.close();
			Log.d("Check", "End of saveMessage()");
		} 
		catch (Exception e) {
			Log.e("Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void addListenerOnButton() {
		 
		post = (ImageButton) findViewById(R.id.bPost);
		captionEditText = (EditText) findViewById(R.id.txtUserCaption);
		
		post.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				caption = captionEditText.getText().toString();
				Log.d("Check", caption);
				saveMessage(arg0);
				Intent openMain = new Intent ("com.sehack1.myday.MAINSCREEN");
				startActivity(openMain);
			}
 
		});

	}
	
}