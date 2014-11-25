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
    
    public Board(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
        thread = new mThread(holder, context);
	}

    public void setPlayers(ArrayList<Player> p){
    	players = p;
    }
    
    /** Moves the player on the board, must first call setPlayers
     * @param boardIndex integer position of player on board
     * @param playerIndex integer position of player in array
     * @throws Exception 
     */
    public void movePlayer(int boardIndex, int playerIndex) throws IllegalStateException{
    	if(players == null){
    		throw new IllegalStateException("setPlayers has not been called yet, you must" +
    				"call setPlayers(ArrayList<Player> p) first");
    	}
    	try{
    		if(players.get(playerIndex).getImage() == null){
        		throw new IllegalStateException("Must set the players bitmap before moving him");
        	}
    	}catch(IndexOutOfBoundsException e){
    		throw new IndexOutOfBoundsException("You called a player at an index that didn't exist");
    	}
    	thread.movePlayer(boardIndex, playerIndex);
    }
    
	public mThread getThread(){
		return thread;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
        thread.setTextSize(mTextSize);
		
        Canvas mCanvas = holder.lockCanvas();
		//TODO remove, just for testing
    	ArrayList<Square> list = new ArrayList<Square>();
    	for(int x = 0; x < 40; x++)
    		list.add(new BasicSquare(10, 10, "testName"));
       	thread.setUp(list, mCanvas.getWidth(), mCanvas.getHeight());
       	holder.unlockCanvasAndPost(mCanvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
        thread.setSurfaceSize(width, height);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			}catch(InterruptedException e){
				//do nothing
			}
		}
	}
	
	public void setTextSize(int t){
		mTextSize = t;
	}
}
		