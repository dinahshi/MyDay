package com.sehack1.myday;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class NewDayScreen extends Activity implements View.OnClickListener{

	private ImageButton takePic;
	private ImageButton choosePic;
	private Uri mImgUri = null;
	
    public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int RESULT_LOAD_IMG = 1;
	public static final int RESULT_TAKE_PIC = 2;
	public final static String MESSAGE = "com.sehack1.myday.PICTUREPATH";

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
				Log.d ("tag", "starting camera intent");
				Intent capture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
				mImgUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				capture.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);
				startActivityForResult(capture, RESULT_TAKE_PIC);
				break;
			case R.id.bChoosePic:
				Intent i = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMG);
				break;
		}
	}
	
	private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
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
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE){
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                                    "IMG_"+ timeStamp + ".jpg");
            } else {
                    return null;
            }

            return mediaFile;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == RESULT_TAKE_PIC && resultCode == RESULT_OK) {
	        Log.d("tag", "picture saved in: " + mImgUri.toString());
	        
	        // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, mImgUri);
            startActivity(openConfirm);	
		}
			
		if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null)
		{
			mImgUri = data.getData();
	        Log.d("tag", "picture retrieved from: " + mImgUri.toString());

            // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, mImgUri.toString());
			Log.d("tag", "startactivity next");
            startActivity(openConfirm);		            
		}
	}
	
	/*private String getPathFromURI(Uri contentUri) {
		String[] proj = {MediaStore.Images.Media.DATA};
	    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	    cursor.moveToFirst();
	    int columnIndex = cursor.getColumnIndex(proj[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }*/
}
