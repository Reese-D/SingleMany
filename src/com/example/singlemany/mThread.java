package com.example.singlemany;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.view.SurfaceHolder;

public class mThread extends Thread{
	SurfaceHolder mSurfaceHolder;
	Context mContext;
	Handler mHandler;
	Paint mPaint;
	int mTextSize;
	Canvas mCanvas = null;
	boolean surfaceChanged = true;
	
	//get view dimensions
	int surfaceWidth, surfaceHeight;
	
	//set running loop and timer
	boolean running = true;
	long timestamp = 0;
	
	//set up board properties
	int numSquares;
	
	boolean screenChanged = false;
	
	//set up currentPlayer properties
	float currentX, currentY, futureX, futureY;
	
	//get timers for velocity and fps
	double framesPerSecond = 0;
	long oldTime = SystemClock.elapsedRealtime();
	long currentTime = 0;
	
	//set array of boxes to be drawn
	//public ArrayList<RectF> drawnSquares = new ArrayList<RectF>();
	
	//size of each square
	private int squareHeight;
	private int squareWidth;
	final private int SIDES = 4;
	
	ArrayList<Player> players;
	boolean playersInitialized = false;
	
	//setup a list of all the Squares
	ArrayList<Square> drawnSquares;
	private String TAG = "1";
	
	
	
	/**
	 * @param surfaceHolder the surface we'll use to draw
	 * @param context the class that created this
	 */
	public mThread(SurfaceHolder surfaceHolder, Context context) {
		//copy constructor inputs for later use
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		

	}
	
	/**
	 * @param t text size for square names
	 */
	public void setTextSize(int t){
		mTextSize = t;
	}
	
	/**
	 * @param list all the squares on the board
	 * @param canvasWidth the canvas's width
	 * @param canvasHeight the canvas's height
	 * @param players all of the players in the game
	 */
	public void setUp(ArrayList<Square> list, int canvasWidth, int canvasHeight, ArrayList<Player> players){
		
		//initialize arrays
		drawnSquares = list;
		this.players = players;
		
		//find the number of squares on each side of the board
		numSquares = ((list.size() / 4));
		squareHeight = (canvasHeight / (numSquares + 1)) - 1;
		squareWidth = (canvasWidth / (numSquares + 1)) - 1;
		
		//set up an array of (numSquares) draw able rectangles to be displayed
		setUpArray();
		

	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run(){
		super.run();
		while(running){
			
			//get time in order to calculate fps
			currentTime = SystemClock.elapsedRealtime();
			framesPerSecond = (1.0/(currentTime - oldTime));
			oldTime = SystemClock.elapsedRealtime();
			try{
				//if we draw before this we'll try drawing from drawnSquares which is empty
				if(!mSurfaceHolder.getSurface().isValid())
					continue;
				
				//lock canvas so we can draw, and also get the canvas to draw on
				mCanvas = mSurfaceHolder.lockCanvas(null);
				
				//draw the board
				doDraw(mCanvas);

			}finally{
				if(mCanvas != null){
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
		}
	}
	
	/** Draws all the squares to the board and sets their name as well
	 * @param c the canvas to draw to
	 */
	private void doDraw(Canvas c){

		c.drawColor(Color.BLACK);
		for(Square element : drawnSquares){
			
			//get the squares paint and rectangular dimensions
			//Paint myColor = setColor(((Square)element).getPaint().getColor());
			Paint myColor = element.getPaint();
			RectF theDrawable = ((Square) element).getRectF();
			int i;
			//draw the rectangle
			if(theDrawable == null){
				i = 0;
			}
			if(myColor == null)
				i = 1;
			if(c == null)
				i = 2;
			c.drawRoundRect(theDrawable, 25f, 25f, myColor);
			
			//get paint for border and draw it
			myColor = element.getmPaintRim();
			c.drawRoundRect(theDrawable, 25f, 25f, myColor);
			
			//get the name so we can draw it on the square
			String theText = ((Square)element).getName();
			
			//set the CENTER of the squares x and y so that later players can know where to move to
			((Square)element).setX((theDrawable.right - theDrawable.left)/2 + theDrawable.left);
			((Square)element).setY((theDrawable.bottom - theDrawable.top)/2 + theDrawable.top);
			
			//get text passed down from dimension -> Main -> Board -> here
			myColor.setTextSize(mTextSize);
			
			//find text actual length by multiplying the number of characters times the size of the text
			//multiply the size of the text by 0.6 because text size given is in height and width of text
			//is 3/5 of the texts height, then divide by 2 to center the text.
			//If the square has no name theText could be null
			if(theText != null)
				c.drawText(theText, (float) (theDrawable.centerX() - (theText.length() *(mTextSize * 0.6))/2), theDrawable.centerY(), myColor);

			
		}
	}
	
	/** Sets up the border for the squares so the other method isn't so large
	 * @param color color of the border
	 * @return Paint object for a decent border
	 */
	private Paint setBorder(int color){
		Paint newColor = new Paint();
		newColor.setColor(color);
		newColor.setStyle(Paint.Style.STROKE);
		newColor.setStrokeWidth(2f);
		newColor.setAntiAlias(true);
		return newColor;
	}
	
	/**
	 * @param color the color the paint should be
	 * @return the paint object with the given color, for the square
	 */
	private Paint setColor(int color){
		//create new color and fill it in
		Paint newColor = new Paint();
		newColor.setColor(color);
		newColor.setStyle(Paint.Style.FILL);
		newColor.setAntiAlias(true);
		return newColor;
	}
	
	//TODO fix bug, drawing the last 4 squares on top of eachother
	/** Sets up the Squares in the array by finding where each should be drawn and
	 *  setting their RectF property
	 */
	private void setUpArray(){
		
		//first square will be in the top left corner
		float tempIntLeft = 0;
		float tempIntTop = 0;
		float tempIntRight = (float) squareWidth;
		float tempIntBottom =  (float) squareHeight;
		
		//some counters so we know which square / side we're on
		int counter = 0;
		//the current side: 0 being the top 3 being the left
		int counter2 = 0;
		for(Square element : drawnSquares){
			//draw
			element.setRectF(new RectF(tempIntLeft, tempIntTop, tempIntRight, tempIntBottom));
			
			//if you're on the top of the screen, move the square to the right
			if(counter2 == 0){
				tempIntLeft += squareWidth + 1;
				tempIntRight += squareWidth + 1;
			}
			//" " left " " " bottom " " " 
			if(counter2 == 2){ 
				tempIntLeft -= squareWidth + 1; 
				tempIntRight -= squareWidth + 1;
			}
			//" " down " " " right " " " 
			if(counter2 == 1){
				tempIntTop += squareHeight + 1;
				tempIntBottom += squareHeight + 1;
			}
			//" " up " " " left " " " 
			if(counter2 == 3){ 
				tempIntTop -= squareHeight + 1; 
				tempIntBottom -= squareHeight + 1;
			}
			//move to the next square
			counter++;
			
			//if we're done drawing all the squares on one side, move to the next
			if(counter == numSquares){
				counter = 0;
				counter2++;
			}
		}
	}
	
    /** Moves a given player to specified location
     * @param boardIndex index the player wants to move to
     * @param p Player instance that should move
     */
    public void movePlayer(int boardIndex, Player p){
    	p.setMovingTo(boardIndex);
    }
	
 
	/** start or stop the run() method
	 * @param b determines whether the board will continue to be redrawn
	 */
	public void setRunning(Boolean b){
		running = b;
	}

	/** updates the canvas's dimensions if they change
	 * @param width new canvas width
	 * @param height new canvas height
	 */
	public void setSurfaceSize(int width, int height) {
		squareHeight = height / (numSquares + 1) - 1;
		squareWidth = width / (numSquares + 1) - 1;
		
	}
}