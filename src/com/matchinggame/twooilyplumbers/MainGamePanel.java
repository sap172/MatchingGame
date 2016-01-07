package com.matchinggame.twooilyplumbers;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


//Goes to this panel after the main screen
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	//global variables
	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private int textX,textY;
	private String displayText;
	private NamedColor[] colorsArray;
	private NamedColor correctChoice;
	Paint paint;
	private NamedColor currentColor;
	private NamedColor choice1Color,choice2Color,choice3Color,choice4Color;
	private int choice1X,choice1Y;
	private int choice2X,choice2Y;
	private int choice3X,choice3Y;
	private int choice4X,choice4Y;
	
	private int screenWidth;
	private int screenHeight;
	private int mode;
	private int totalScore;
	private boolean gameOver;
	private Timer gameTimer;
	private int timeLeft;
	private boolean stopTimer;
	public static final int INITIAL_TIME = 10;
	
	MainActivity parent;

	public MainGamePanel(Context context, int mode) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		//get screen dimensions
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		
		paint = new Paint();

		//sets the mode
		//1 is normal mode, 2 is challenge mode
		this.mode = mode;
		//System.out.println("Mode selected: " + this.mode);
		
		//setups the game
		setupGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		if((thread.getRunning()) != true){
			thread = new MainThread(getHolder(), this);
			thread.setRunning(true);
			thread.start();
		}
		stopTimer = false;
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");

		//pause game timer
		stopTimer = true;
		
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
		
	}
	
	public void checkStatus(NamedColor buttonPressed){
				
		//checks the status of the game
		switch(mode){
		case(1): //normal game
			
			if(buttonPressed.getIntValue() == correctChoice.getIntValue()){
				totalScore += 10;
			}
			else{
				if(totalScore > 0)
					totalScore -= 5;
			}
		break;
		case(2): //challenge mode

			if(buttonPressed.getIntValue() == correctChoice.getIntValue()){
				totalScore += 10;
			}
			else{
				if(totalScore > 0)
					totalScore -= 5;
			}
		break;
		}

		//print total score
		//System.out.println("Total Score: " + totalScore);
		
		//go to the next colors
    	nextColor();
	}
	
	public void setupGame(){
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		//sets gameover to false
		gameOver = false;
		
		//resets the total score
		totalScore = 0;
		
		//sets the game timer
		gameTimer = new Timer();
		
		//sets the x and y for the text
		textX = screenWidth/2;
		textY = screenHeight/2;
		
		if(mode == 1 || mode == 2){
			//sets up the first challenge
			setupColors();
		}
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	//need a timertask for the timer
	class RemindTask extends TimerTask {
        public void run() {
        	
        	if(stopTimer == false){
        	//lower the time left
        	if(timeLeft > 0){
        		timeLeft--;
        		//System.out.println("Time left: " + timeLeft);
        	}
        	else{
        		gameOver = true;
        		System.out.println("Game Over");
        		gameOver();
        		gameTimer.cancel();
        	}
        	}
        }
	}
	
	public void setupColors(){
		//add the colors to the array
		if(mode == 1){
			colorsArray = new NamedColor[]{NamedColor.RED,NamedColor.BLUE,NamedColor.GREEN,NamedColor.YELLOW,NamedColor.ORANGE};
		}
		else if(mode == 2){
			//challenge mode
			colorsArray = new NamedColor[]{NamedColor.RED,NamedColor.BLUE,NamedColor.GREEN,NamedColor.YELLOW,NamedColor.ORANGE,
					NamedColor.AMETHYST,NamedColor.BABYPINK,NamedColor.CHOCOLATE,NamedColor.CRANBERRY,NamedColor.CRIMSON,NamedColor.DARKRED,
					NamedColor.DENIM,NamedColor.FUCHSIA,NamedColor.GARNET,NamedColor.GOLD,NamedColor.GRAY,NamedColor.HONEY,NamedColor.HOTPINK,
					NamedColor.LAVENDER,NamedColor.OLIVE,NamedColor.PURPLE,NamedColor.ROYALBLUE,NamedColor.SALMON,NamedColor.TAFFY,NamedColor.TEAL,
					NamedColor.TURQUOISE};
		}
		
		//set ups the game for colors
		nextColor();
		
		//sets the x and y for choice 1
		choice1X = 0;
		choice1Y = 0;
		
		//sets the x and y for choice 2
		choice2X = 0;
		choice2Y = screenHeight-240;
		
		//sets the x and y for choice 3
		choice3X = 2*(screenWidth/3);
		choice3Y = screenHeight-240;
		
		//sets the x and y for choice 4
		choice4X = 2*(screenWidth/3);
		choice4Y = 0;
		
		//finishes setting up the timer
		timeLeft = INITIAL_TIME;
		stopTimer = false;
		gameTimer.scheduleAtFixedRate(new RemindTask(),1,1000);
	}
	
	public void gameOver(){
		//prompts the user if they want to play again
		parent.displayGameOver(totalScore);
		
		//at this point start a new game
		setupGame();
		
		stopTimer = true;
	}
	
	public void stopTimer(boolean bol){
		//sets the timer to start/stop running
		stopTimer = bol;
	}
	
	public void setParent(MainActivity parent){
		//gets the parent
		this.parent = parent;
	}
	
	public String getDisplayText(){
		//returns the display text to check if tilted in the right direction
		return displayText;
	}
	
	public void nextColor(){
		//function sets up the next color

		if(gameOver == false){
		
		int random1,random2,random3,random4;
		
		//establish random numbers
		random1 = (int)(Math.random()*colorsArray.length);
		random2 = (int)(Math.random()*colorsArray.length);
		random3 = (int)(Math.random()*colorsArray.length);
		random4 = (int)(Math.random()*colorsArray.length);
		
		while(random2 == random1){
			random2 = (int)(Math.random()*colorsArray.length);
		}
		while(random3 == random1 || random3 == random2){
			random3 = (int)(Math.random()*colorsArray.length);
		}
		while(random4 == random1 || random4 == random2 || random4 == random3){
			random4 = (int)(Math.random()*colorsArray.length);
		}
		
		//the correct color
		switch((int)(Math.random()*4)){
		case(0):
			correctChoice = colorsArray[random1];
		break;
		case(1):
			correctChoice = colorsArray[random2];
		break;
		case(2):
			correctChoice = colorsArray[random3];
		break;
		case(3):
			correctChoice = colorsArray[random4];
		break;
		}
		
		//display text
		displayText = correctChoice.toString();
		
		choice1Color = colorsArray[random1];
		choice2Color = colorsArray[random2];
		choice3Color = colorsArray[random3];
		choice4Color = colorsArray[random4];
		
		//sets the color of the text
		switch((int)(Math.random()*4)){
		case(0):
			currentColor = colorsArray[random1];
		break;
		case(1):
			currentColor = colorsArray[random2];
		break;
		case(2):
			currentColor = colorsArray[random3];
		break;
		case(3):
			currentColor = colorsArray[random4];
		break;
		}
		
		}
	}
	
	public void actionCheck(int x, int y){
		
		
		if(gameOver == false && stopTimer == false){
		int choiceInt = 0;
		
		//if in button 1
		if((x >= choice1X) && (x <= choice1X+screenWidth/3) &&
				(y >= choice1Y) && (y <= choice1Y+200)){
			choiceInt = 1;
		}
		
		//if in button 2
		if((x >= choice2X) && (x <= choice2X+screenWidth/3) &&
				(y >= choice2Y) && (y <= choice2Y+200)){
			choiceInt = 2;
		}
		
		//if in button 3
		if((x >= choice3X) && (x <= screenWidth) &&
				(y >= choice3Y) && (y <= choice3Y+200)){
			choiceInt = 3;
		}
		
		//if in button 4
		if((x >= choice4X) && (x <= screenWidth) &&
				(y >= choice4Y) && (y <= choice4Y+200)){
			choiceInt = 4;
		}
		
		switch(choiceInt){
    	case(1):
    		//System.out.println("1");
    		checkStatus(choice1Color);
    	break;
    	case(2):
    		//System.out.println("2");
    		checkStatus(choice2Color);
		break;
    	case(3):
    		//System.out.println("3");
    		checkStatus(choice3Color);
		break;	
    	case(4):
    		//System.out.println("4");
    		checkStatus(choice4Color);
    	break;
		}
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int actions = event.getActionMasked();

		switch (actions){

        case MotionEvent.ACTION_DOWN:

        	//checks if buttons are pressed
        	//returns int for button that is pressed
        	
        	if(mode == 1 || mode == 2){
        		//check where the user clicked for colors
        		actionCheck((int)event.getX(), (int)event.getY());
        	}
        	
        break;
		
		}
		return true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.BLACK);

		if(mode == 1 || mode == 2){//normal game or challenge
			drawColors(canvas);
		}
	}
	
	public void drawColors(Canvas canvas){
		//draws the colors canvas
		paint.setStyle(Style.FILL);
		
		// draw button 1
		paint.setAntiAlias(false);
		paint.setColor(choice1Color.getIntValue());
		canvas.drawRect(choice1X, choice1Y, choice1X+screenWidth/3, choice1Y+200, paint);
		
		// draw button 2
		paint.setAntiAlias(false);
		paint.setColor(choice2Color.getIntValue());
		canvas.drawRect(choice2X, choice2Y, choice2X+screenWidth/3, choice2Y+200, paint);
		
		// draw button 3
		paint.setAntiAlias(false);
		paint.setColor(choice3Color.getIntValue());
		canvas.drawRect(choice3X, choice3Y, screenWidth, choice3Y+200, paint);
		
		// draw button 4
		paint.setAntiAlias(false);
		paint.setColor(choice4Color.getIntValue());
		canvas.drawRect(choice4X, choice4Y, screenWidth, choice4Y+200, paint);

		// draw some text using FILL style
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(currentColor.getIntValue());
		paint.setTextSize(120);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(displayText, textX, textY, paint);
		
		// draw some text using STROKE style
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.BLACK);
		paint.setTextSize(120);
		canvas.drawText(displayText, textX, textY, paint);
		

		//display the time left
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("" + timeLeft, screenWidth/2, screenHeight - 150, paint);
		
		//display the total score
		paint.setTextSize(50);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("Score: " + totalScore, screenWidth/2, screenHeight - 80, paint);
	}
}




