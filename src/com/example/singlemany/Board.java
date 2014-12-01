package com.example.singlemany;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Board extends SurfaceView implements SurfaceHolder.Callback {
	private mThread thread;
    int mTextSize;
    ArrayList<Player> players = null;
    ArrayList<Square> squares;
    SurfaceHolder holder;
    Context mContext;
    Canvas mCanvas;
    
    public Board(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
		//set up holder to be used by thread
		holder = getHolder();
		holder.addCallback(this);
		
		//set context to variable so it can be used later
		mContext = context;

	}
    
    /** sets local variables equal to given variables, and creates a new thread
     * @param p ArrayList of players on the board
     * @param list ArrayList of squares on the board
     */
    public void setupBoard(ArrayList<Player> p, ArrayList<Square> list){

    	squares = list;
    	/*this.squares = list;*/
    	players = p;
    
        thread = new mThread(holder, mContext);

    }
    
/*    *//** Moves the player on the board, must first call setPlayers
     * @param boardIndex integer position of player on board
     * @param playerIndex integer position of player in array
     * @throws Exception 
     *//*
    public void movePlayer(int boardIndex, Player p) throws IllegalStateException{
    	if(players == null){
    		throw new IllegalStateException("setPlayers has not been called yet, you must" +
    				"call setPlayers(ArrayList<Player> p) first");
    	}
		if(p.getImage() == null){
    		throw new IllegalStateException("Must set the players bitmap before moving him");
    	}
    	thread.movePlayer(boardIndex, p);
    }*/
    
    
	/**
	 * @return mThread currently in use
	 */
	public mThread getThread(){
		return thread;
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        mCanvas = holder.lockCanvas();
        //after getting the canvas pass it to the thread so it can set up its variables
        //and acquire screen size
		thread.setUp(squares, mCanvas.getWidth(), mCanvas.getHeight(), players);
		holder.unlockCanvasAndPost(mCanvas);
		
		//start the thread
		thread.setRunning(true);
		thread.start();
        thread.setTextSize(mTextSize);
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//if the surface changes notify the thread it has new dimensions to deal with
        thread.setSurfaceSize(width, height);

	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				//if the surface dies kill the thread
				thread.join();
				retry = false;
			}catch(InterruptedException e){
				//do nothing
			}
		}
	}
	
	/**
	 * @param t textSize passed down from mainActivity
	 */
	public void setTextSize(int t){
		mTextSize = t;
	}
}
		