package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NewDayScreen extends Activity{

	ImageButton takePic;

	private static int RESULT_LOAD_IMG = 1;
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
 
		takePic.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {		
				// open up gallery to select an image
				Intent i = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMG);
			} 
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null)
		{
			Uri selectedImage = data.getData();
			String[] filePath = {MediaStore.Images.Media.DATA};
			
			Cursor cursor = getContentResolver().query(selectedImage, filePath, null, null, null);
			cursor.moveToFirst();
			
			int columnIndex = cursor.getColumnIndex(filePath[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, picturePath);
            startActivity(openConfirm);		            
		}
	}
}
