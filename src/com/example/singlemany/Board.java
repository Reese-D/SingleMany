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
		holder = getHolder();
		holder.addCallback(this);
		mContext = context;

	}
    
    public void setupBoard(ArrayList<Player> p, ArrayList<Square> list){
        //TODO remove, this is just for testing and un-comment this.squares = list;
    	squares = new ArrayList<Square>();
    	for(int x = 0; x < 40; x++)
    		squares.add(new BasicSquare(10, 10, "testName"));
    	/*this.squares = list;*/
    	players = p;
    
        thread = new mThread(holder, mContext);
		thread.setUp(squares, mCanvas.getWidth(), mCanvas.getHeight(), players);
		
		thread.setRunning(true);
		thread.start();
        thread.setTextSize(mTextSize);
        

    }
    
    /** Moves the player on the board, must first call setPlayers
     * @param boardIndex integer position of player on board
     * @param playerIndex integer position of player in array
     * @throws Exception 
     */
    public void movePlayer(int boardIndex, Player p) throws IllegalStateException{
    	if(players == null){
    		throw new IllegalStateException("setPlayers has not been called yet, you must" +
    				"call setPlayers(ArrayList<Player> p) first");
    	}
		if(p.getImage() == null){
    		throw new IllegalStateException("Must set the players bitmap before moving him");
    	}
    	thread.movePlayer(boardIndex, p);
    }
    
	public mThread getThread(){
		return thread;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        mCanvas = holder.lockCanvas();

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
		