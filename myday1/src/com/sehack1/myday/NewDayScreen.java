package com.sehack1.myday;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class NewDayScreen extends Activity implements View.OnClickListener{

	private static final String TAG = "NewDayScreen";
	public static final int RESULT_LOAD_IMG = 1;
	public static final int RESULT_TAKE_PIC = 2;
	public final static String MESSAGE = "com.sehack1.myday.PICTUREPATH";

	private ImageButton takePic;
	private ImageButton choosePic;
	private Uri mImgUri = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newdayscreen);
		addListenerOnButton();
		
	}

	
	public void addListenerOnButton() {
		takePic = (ImageButton) findViewById(R.id.bTakePic);
		choosePic = (ImageButton) findViewById(R.id.bChoosePic);
 
		takePic.setOnClickListener(this);
		choosePic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bTakePic:
				Log.d (TAG, "starting camera intent");
				Intent capture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
				mImgUri = getOutputMediaFileUri();
				capture.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);
				startActivityForResult(capture, RESULT_TAKE_PIC);
				Log.d(TAG, "activity started");
				break;
			case R.id.bChoosePic:
				Intent i = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMG);
				break;
		}
	}
	
	private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
	}
	
	/** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.

            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "MyDay");

            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists()){
                    if (! mediaStorageDir.mkdirs()){
                            return null;
                    }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
            return mediaFile;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == RESULT_TAKE_PIC && resultCode == RESULT_OK) {
	        Log.d(TAG, "picture saved in: " + mImgUri.toString());
	        
	        // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, getPathFromURI(mImgUri));
			Log.d(TAG, "start confirm screen");
            startActivity(openConfirm);	
		}
			
		if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null)
		{
			mImgUri = data.getData();
	        Log.d(TAG, "picture retrieved from: " + mImgUri.toString());

            // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, getPathFromURI(mImgUri));
			Log.d(TAG, "start confirm screen");
            startActivity(openConfirm);		            
		}
	}
	
	public String getPathFromURI(Uri contentUri) {
		Cursor cursor = null;
		try { 
		String[] proj = { MediaStore.Images.Media.DATA };
		cursor = getContentResolver().query(contentUri,  proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
		} 
		finally {
			if (cursor != null) {
				cursor.close();
		    }
		}
	}
}