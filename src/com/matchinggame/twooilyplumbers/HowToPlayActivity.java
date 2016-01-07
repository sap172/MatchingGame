package com.matchinggame.twooilyplumbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class HowToPlayActivity extends Activity {
	private static final String TAG = HowToPlayActivity.class.getSimpleName();
	public final static String EXTRA_MESSAGE = "com.matchinggame.twooilyplumbers.HowToPlayActivity.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_howtoplay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void goBack(View view){
		//closes this activity
		HowToPlayActivity.this.finish();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	 
	@Override
	public void onResume(){    
	     Log.d(TAG, "onResume");       
	     super.onResume(); 
	 }
}
