package com.sehack1.myday;

import java.io.FileOutputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ConfirmScreen extends Activity {
	
	public String filePath;
	//String imagePath = "Default";
	public String caption = "No caption chosen";
	public String date = "No date yet";
	public String imageInfo = "Default";
	
	EditText captionEditText;
	ImageButton post;
	
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm);
		
		// Paint the image 
		Intent i = getIntent();
		filePath = i.getStringExtra(NewDayScreen.MESSAGE);		 
        ImageView imageView = (ImageView) findViewById(R.id.ivUserPic);
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        
		addListenerOnButton();
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
