package com.matchinggame.twooilyplumbers;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity{
	
	private AdView adView;
	LinearLayout surfaceLay;
	MainGamePanel panel;
	final Context context = this;
	float[] gravity;
	private int mode;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Adds the gamepanel and ad onto screen
		createView();
		
	}
	
	public void displayGameOver(int score){

		final int temp = score;
		
		this.runOnUiThread(new Runnable() {

	        public void run() {
			    
				//updates the high score
				updateHighScore(temp);
	        	
	            // TODO Auto-generated method stub
	        	/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	    				context);
	    		// set title
	    					alertDialogBuilder.setTitle("Game Over!");
	    		 
	    					// set dialog message
	    					alertDialogBuilder
	    						.setMessage("Total Score: " + temp)
	    						.setCancelable(false)
	    						.setPositiveButton("Return to Menu",new DialogInterface.OnClickListener() {
	    							public void onClick(DialogInterface dialog,int id) {
	    								
	    								
	    								// if this button is clicked, close
	    								// current activity
	    								MainActivity.this.finish();
	    							}
	    						  })
	    						.setNegativeButton("Keep Playing",new DialogInterface.OnClickListener() {
	    							public void onClick(DialogInterface dialog,int id) {
	    								// if this button is clicked, just close
	    								// the dialog box and do nothing
	    								dialog.cancel();
	    								panel.setRunTimer(false);
	    							}
	    						});
	    		 
	    						// create alert dialog
	    						AlertDialog alertDialog = alertDialogBuilder.create();
	    		 
	    						// show it
	    						alertDialog.show();
				
*/
				//display the gameover popup
				LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);  
			    View popupView = layoutInflater.inflate(R.layout.activity_gameover_popup, null);  
			             final PopupWindow popupWindow = new PopupWindow(
			               popupView, 
			               LayoutParams.WRAP_CONTENT,  
			                     LayoutParams.WRAP_CONTENT);  
			             
	             Button btnKeepPlaying = (Button)popupView.findViewById(R.id.btn_keep_playing);
	             Button btnBack = (Button)popupView.findViewById(R.id.btn_popup_back);
	             
	             //back to main menu
	             btnBack.setOnClickListener(new Button.OnClickListener(){
			     @Override
			     public void onClick(View v) {
			    	 // TODO Auto-generated method stub
			    	 popupWindow.dismiss();
		             MainActivity.this.finish();
			     }});
	             
	             //keep playing button
	             btnKeepPlaying.setOnClickListener(new Button.OnClickListener(){
			     @Override
			     public void onClick(View v) {
				      // TODO Auto-generated method stub
				      popupWindow.dismiss();
				      panel.stopTimer(false);
			     }});
			        
	             
	             //center popup
	             popupWindow.showAtLocation(surfaceLay, 17, 0, 0);
				
	        	}
	        	
	        
	        });
	}
	
	public void updateHighScore(int score){
		
		//get scores
		SharedPreferences prefs = this.getSharedPreferences("com.matchinggame.twooilyplumbers.HIGHSCORES", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		int score1 = prefs.getInt("score1", 0); //0 is the default value
		int score2 = prefs.getInt("score2", 0); //0 is the default value
		int score3 = prefs.getInt("score3", 0); //0 is the default value
		
		//Reset values in file
		editor.putInt("score1", 0);
		editor.putInt("score2", 0);
		editor.putInt("score3", 0);
		editor.commit();
		
		//check to see if score is high enough
		if(score > score3){
			
			//popup if player wants to add high score
			LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);  
		    View popupView = layoutInflater.inflate(R.layout.highscore_popup, null);  
		             final PopupWindow popupWindow = new PopupWindow(
		               popupView, 
		               LayoutParams.WRAP_CONTENT,  
		                     LayoutParams.WRAP_CONTENT);  
		             
             Button btnSubmit = (Button)popupView.findViewById(R.id.btn_submit);
                          
             //submit button
             btnSubmit.setOnClickListener(new Button.OnClickListener(){
		     @Override
		     public void onClick(View v) {
			      // TODO Auto-generated method stub
			      popupWindow.dismiss();
			      panel.stopTimer(false);
		     }});
		        
             
             //center popup
             popupWindow.showAtLocation(surfaceLay, 17, 0, 0);
    						
			//moves the other scores
			if(score > score2){
				editor.putInt("score3", score2);
				
				if(score > score1){
					editor.putInt("score2", score1);
					editor.putInt("score1", score);
				}
				else{
					editor.putInt("score2", score);
				}
			}
			else{
				editor.putInt("score3", score);
			}
					
			//stores the score
			editor.commit();
		}
	}
	  
    public void createView(){

    	//variables
		final LinearLayout adLay = (LinearLayout)findViewById(R.id.adLayout);
		surfaceLay = (LinearLayout)findViewById(R.id.surfaceView);		
		
		//gets the mode
		Intent tempIntent = getIntent();
		mode = (int)tempIntent.getIntExtra("mode", 0);
		panel = new MainGamePanel(this,mode);
		panel.setParent(this);
		
		//add game panel
		surfaceLay.addView(panel);
		
		//creates the ad
    	// Create the adView.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-6836633067086907/7996382870");
        adView.setAdSize(AdSize.BANNER);


        // Initiate a generic request.
        //phone id: A8F61D3D70BC28D64A26A5B09D3B046D
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice("A8F61D3D70BC28D64A26A5B09D3B046D") // test phone
        .build();
        
        // Load the adView with the ad request.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener()
        {
        	boolean firstTime = true;
        	@Override
        	public void onAdLoaded()
        	{
        	if(firstTime)
        	{
        	firstTime = false;
        	adLay.addView(adView);
        	}
        	}
        });
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	   
    @Override
    public void onPause() {
      adView.pause();
      super.onPause();
      
    }

    @Override
    public void onResume() {
      super.onResume();
      adView.resume();
    }

    @Override
    public void onDestroy() {
      adView.destroy();
      super.onDestroy();
    }
}
