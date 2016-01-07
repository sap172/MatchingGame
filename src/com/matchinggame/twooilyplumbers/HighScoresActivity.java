package com.matchinggame.twooilyplumbers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HighScoresActivity extends Activity {
	private static final String TAG = HighScoresActivity.class.getSimpleName();
	public final static String EXTRA_MESSAGE = "com.matchinggame.twooilyplumbers.HighScoresActivity.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		
		//set the scores
		grabScores();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void grabScores(){
		//gets the scores from the file
		SharedPreferences prefs = this.getSharedPreferences("com.matchinggame.twooilyplumbers.HIGHSCORES", Context.MODE_PRIVATE);
		int score1 = prefs.getInt("score1", 0); //0 is the default value
		int score2 = prefs.getInt("score2", 0); //0 is the default value
		int score3 = prefs.getInt("score3", 0); //0 is the default value
		
		//get the views
		TextView view1 = (TextView) findViewById(R.id.txt_score1);
		TextView view2 = (TextView) findViewById(R.id.txt_score2);
		TextView view3 = (TextView) findViewById(R.id.txt_score3);
		
		//set the text
		view1.append("Name 1: " + score1);
		view2.append("Name 2: " + score2);
		view3.append("Name 3: " + score3);

	}
	
	public void goBack(View view){
		//closes this activity
		HighScoresActivity.this.finish();
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
