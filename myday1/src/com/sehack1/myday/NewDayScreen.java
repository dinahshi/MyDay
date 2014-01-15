package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class NewDayScreen extends Activity{

	ImageButton takePic;

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
				Intent openConfirm = new Intent ("com.sehack1.myday.CONFIRMSCREEN");
				startActivity(openConfirm);
			}
 
		});

	}
}
