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

public class NewDayScreen extends Activity implements View.OnClickListener{

	private ImageButton takePic;
	private ImageButton choosePic;
	private String filePath;
	
	private static int RESULT_LOAD_IMG = 1;
	private static int RESULT_TAKE_PIC = 2;
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
				Intent capture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(capture, RESULT_TAKE_PIC);
				break;
			case R.id.bChoosePic:
				Intent i = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMG);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK && data != null)
		{
			Uri selectedImage = data.getData();
			filePath = getPathFromURI(selectedImage);
            
            // Open up captions page
            Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
			openConfirm.putExtra(MESSAGE, filePath);
            startActivity(openConfirm);		            
		}
	}
	
	private String getPathFromURI(Uri contentUri) {
		String[] proj = {MediaStore.Images.Media.DATA};
	    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
	    cursor.moveToFirst();
	    int columnIndex = cursor.getColumnIndex(proj[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
