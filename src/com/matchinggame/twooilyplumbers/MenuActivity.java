package com.matchinggame.twooilyplumbers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MenuActivity extends Activity {

	private static final String TAG = MenuActivity.class.getSimpleName();
	public final static String EXTRA_MESSAGE = "com.matchinggame.twooilyplumbers.MenuActivity.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		
		//Set the title size
		int parentHeight = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.img_title).getLayoutParams();
		params.height = parentHeight/2 - 120;
		findViewById(R.id.img_title).setLayoutParams(params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	//Uses View instead of MenuItem because coming from activity_menu.xml instead of action bar
	public void normalGame(View view){
		//function opens up a new activity with mode 1
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("mode", (int)1);
    	startActivity(intent);
		
	}
	
	public void challengeGame(View view){
		//function opens up a new activity with mode 2
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("mode", 2);
    	startActivity(intent);
	}
	public void howPlay(View view){
		//function opens up how to play
    	Intent intent = new Intent(this, HowToPlayActivity.class);
    	startActivity(intent);
		
	}
	public void highScores(View view){
		//function opens up high scores
    	Intent intent = new Intent(this, HighScoresActivity.class);
    	startActivity(intent);
		
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
