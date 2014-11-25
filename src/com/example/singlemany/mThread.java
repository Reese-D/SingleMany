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
import android.webkit.WebView.FindListener;

public class mThread extends Thread{
	SurfaceHolder mSurfaceHolder;
	Context mContext;
	Handler mHandler;
	Paint mPaint;
	int mTextSize;
	
	//get view dimensions
	int surfaceWidth, surfaceHeight;
	
	//set running loop and timer
	boolean running = true;
	long timestamp = 0;
	
	//set up board properties
	int numSquares;
	
	boolean screenChanged = false;
	double positionX, positionY;
	
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
	
	//setup a list of all the Squares
	ArrayList<Square> drawnSquares;
	
	
	
	public mThread(SurfaceHolder surfaceHolder, Context context) {
		
		//copy constructor inputs for later use
		mSurfaceHolder = surfaceHolder;
		mContext = context;
		

	}
	
	public void setTextSize(int t){
		mTextSize = t;
	}
	
	public void setUp(ArrayList<Square> list, int canvasWidth, int canvasHeight){
		
		//initialize squares
		drawnSquares = list;
		
		//find the number of squares on each side of the board
		numSquares = ((list.size() / 4) - 1);
		squareHeight = canvasHeight / (numSquares + 1) - 1;
		squareWidth = canvasWidth / (numSquares + 1) - 1;
		//set up an array of (numSquares) draw able rectangles to be displayed
		setUpArray();
	}

	@Override
	public void run(){
		super.run();
		Canvas mCanvas = null;
		while(running){
			currentTime = SystemClock.elapsedRealtime();
			framesPerSecond = (1.0/(oldTime - currentTime));
			oldTime = SystemClock.elapsedRealtime();
			try{
				//if we draw before this we'll try drawing from drawnSquares which is empty
				/*if(!setUpComplete)
					continue;*/
				if(!mSurfaceHolder.getSurface().isValid())
					continue;
				mCanvas = mSurfaceHolder.lockCanvas(null);
				doDraw(mCanvas);
			}finally{
				if(mCanvas != null){
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
		}
	}
	
	public void doDraw(Canvas c){
		//c.drawARGB(255, 155, 55, 5);

		Paint myColor = setColor(Color.BLUE);
		for(Square element : drawnSquares){
			myColor = setColor(((BasicSquare)element).getPaint().getColor());
			RectF theDrawable = ((BasicSquare) element).getRectF();
			c.drawRoundRect(theDrawable, 25f, 25f, myColor);
			myColor = setBorder(Color.BLACK);
			c.drawRoundRect(theDrawable, 25f, 25f, myColor);
			String theText = ((BasicSquare) element).getName();
			
			//get text passed down from dimension -> Main -> Board -> here
			myColor.setTextSize(mTextSize);
			
			//find text actual length by multiplying the number of characters times the size of the text
			//multiply the size of the text by 0.6 because text size given is in height and width of text
			//is 3/5 of the texts height, then divide by 2 to center the text.
			c.drawText(theText, (float) (theDrawable.centerX() - (theText.length() *(mTextSize * 0.6))/2), theDrawable.centerY(), myColor);
			
			
		}
	}
	
	private Paint setBorder(int color){
		Paint newColor = new Paint();
		newColor.setColor(color);
		newColor.setStyle(Paint.Style.STROKE);
		newColor.setStrokeWidth(2f);
		newColor.setAntiAlias(true);
		return newColor;
	}
	
	private Paint setColor(int color){
		//create new color and fill it in
		Paint newColor = new Paint();
		newColor.setColor(color);
		newColor.setStyle(Paint.Style.FILL);
		newColor.setAntiAlias(true);
		return newColor;
	}
	
	private void setUpArray(){
		float tempIntLeft = 0;
		float tempIntTop = 0;
		float tempIntRight = (float) squareWidth;
		float tempIntBottom =  (float) squareHeight;
		int counter = 1;
		int counter2 = 0;
		for(Square element : drawnSquares){

			((BasicSquare) element).setRectF(new RectF(tempIntLeft, tempIntTop, tempIntRight, tempIntBottom));
			
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
			counter++;
			if(counter == numSquares + 1){
				counter = 1;
				counter2++;
			}
		}
	}
	
    public void movePlayer(int boardIndex, int playerIndex){
    	//TODO finish method
    }
	
	public void setRunning(Boolean b){
		running = b;
	}

	public void setSurfaceSize(int width, int height) {
		squareHeight = height / (numSquares + 1) - 1;
		squareWidth = width / (numSquares + 1) - 1;
		
	}
}