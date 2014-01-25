package com.sehack1.myday;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ConfirmScreen extends Activity {
	
	private static final String TAG = "ConfirmScreen";

	private ImageView imageView;
	public String filePath;
	public Uri mImgUri;
	public String caption = "No caption chosen";
	public String date = "No date yet";
	public String imageInfo = "Default";

	private EditText captionEditText;
	private ImageButton post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm);
		
		// Paint the image 
		Intent i = getIntent();
		mImgUri = Uri.parse(i.getStringExtra(NewDayScreen.MESSAGE));	
		filePath = mImgUri.getPath();
		System.out.println("filepath = " + filePath);
        imageView = (ImageView) findViewById(R.id.ivUserPic);
        
		// async bitmap loading
		new AsyncTask <Void, Void, Bitmap>(){
		    @Override
		    protected Bitmap doInBackground(Void... params) {
		        return decodeSampledBitmapFromFilepath(filePath, 450);
		    }

		    @Override
		    protected void onPostExecute(Bitmap bitmap) {
		    	imageView.setImageBitmap(bitmap);
		    }
		}.execute();

		addListenerOnButton();
	}
		
	public static Bitmap decodeSampledBitmapFromFilepath(String filePath, int reqWidth) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
	
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth);
	
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
		// Raw height and width of image
		final int width = options.outWidth;
		int inSampleSize = 1;
	
		if (width > reqWidth) {
	
			final int halfWidth = width / 2;
	
			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
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
	
	// send this into parse @Luisa
	public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
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
}