package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity{

	Animation animLogo;
	ImageView logo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysplash);
		logo = (ImageView) findViewById(R.id.ivLogo);
		
		Thread timer = new Thread(){
			public void run(){
			try{
				animLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
				logo.startAnimation(animLogo);
				sleep(2500);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			finally{
				Intent openMain = new Intent ("com.sehack1.myday.MAINSCREEN");
				startActivity(openMain);
			}
			}
			};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
	}

	
}
