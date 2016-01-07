package com.matchinggame.twooilyplumbers;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

//Allows the game to run on a background thread
public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private Object mPauseLock = new Object();  
	private boolean mPaused;	
	private boolean running;
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean getRunning(){
		return this.running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing on the surface
			try {
				canvas = this.surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					// update game state 
					// draws the canvas on the panel
					if (canvas != null){					
						this.gamePanel.onDraw(canvas);		
					}
				}
			} finally {
				// in case of an exception the surface is not left in 
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
	}
}
