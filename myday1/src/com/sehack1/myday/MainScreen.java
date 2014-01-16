package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainScreen extends Activity {
	
	ImageButton newDay;
	Animation animMain;
	LinearLayout postFeed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		

		postFeed = (LinearLayout) findViewById(R.id.llPostFeed);
		animMain = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		
		//starts animation
		postFeed.startAnimation(animMain);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		 
		newDay = (ImageButton) findViewById(R.id.bNewDay);
 
		newDay.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				//Intent openND = new Intent ("com.sehack1.myday.NEWDAY");
				//startActivity(openND);
				
				Intent openNewDay = new Intent ("com.sehack1.myday.NEWDAY");
				startActivity(openNewDay);
			} 
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	
	}

}
