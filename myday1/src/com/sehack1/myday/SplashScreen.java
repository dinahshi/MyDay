package com.sehack1.myday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends Activity{

	Animation animLogo;
	Animation animLogin;
	LinearLayout userLogin;
	ImageView logo;
	Button signin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysplash);
		
		userLogin = (LinearLayout) findViewById(R.id.llLogin);
		logo = (ImageView) findViewById(R.id.ivLogo);

		
		animLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		
		logo.startAnimation(animLogo);
		addListenerOnButton();
		
		
	}

	
	public void addListenerOnButton() {
		 
		signin = (Button) findViewById(R.id.bSignIn);
 
		signin.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				

						animLogin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
						userLogin.startAnimation(animLogin);
						logo.startAnimation(animLogin);
						

		                Thread timer = new Thread(){
	                        public void run(){
	                        try{
	                                sleep(500);
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
		});	
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
	}

}
