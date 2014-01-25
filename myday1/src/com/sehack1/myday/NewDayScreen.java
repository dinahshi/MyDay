package com.sehack1.myday;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NewDayScreen extends Activity implements View.OnClickListener{

	private static int RESULT_LOAD_IMG = 1;
	private static int RESULT_TAKE_PIC = 2;
	public final static String MESSAGE = "com.sehack1.myday.PICTUREPATH";
	public final static String TAG = "NewDayScreen";
	
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
		switch(v.getId()){
			case R.id.bTakePic:
				Intent capture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
				mImgUri = getOutputMediaFileUri();
				capture.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);
                startActivityForResult(capture, RESULT_TAKE_PIC);
				break;
			case R.id.bChoosePic:
				Intent choose = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(choose, RESULT_LOAD_IMG);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK)
		{
			//Log.d(TAG, "photo saved to: " + mImgUri.toString());
			if (requestCode == RESULT_TAKE_PIC){
	            // Open up captions page
	            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
				openConfirm.putExtra(MESSAGE, mImgUri.toString());
	            Log.d (TAG, "confirm activity started");
				startActivity(openConfirm);	
			}
			else if (requestCode == RESULT_LOAD_IMG){
				mImgUri = data.getData();
	            Log.d(TAG, "photo retrieved from: " + mImgUri.toString());
	            
	            // Open up captions page
	            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
				openConfirm.putExtra(MESSAGE, getPathFromUri(mImgUri));
	            Log.d(TAG, "confirm activity started");
				startActivity(openConfirm);	
			}
		}
	}
	
	private static Uri getOutputMediaFileUri(){
		Log.d(TAG, "in getOutputMediaFileUri");
		return Uri.fromFile(getOutputMediaFile());
	}
	
	private static File getOutputMediaFile(){
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyDay");
				
		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
		        if (! mediaStorageDir.mkdirs()){
		                Log.d("MyCameraApp", "failed to create directory");
		                return null;
		        }
		}
		
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
		
		return mediaFile;
	}
	
	private String getPathFromUri(Uri contentUri){
		String[] proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		cursor.moveToFirst();
		
		int columnIndex = cursor.getColumnIndex(proj[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
	}
}