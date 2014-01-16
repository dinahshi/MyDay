package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ConfirmScreen extends Activity {
	
	public String filePath;
	
	ImageButton post;
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
 
		post.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				Intent openMain = new Intent ("com.sehack1.myday.MAINSCREEN");
				startActivity(openMain);
			}
 
		});

	}
	
}
