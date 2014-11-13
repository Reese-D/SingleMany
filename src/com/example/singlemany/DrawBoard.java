package com.example.singlemany;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * @author Reese De Wind
 * @version 0.0
 */
public class DrawBoard extends View{

	public static int SIDES = 4;
	private int numSquares = 9;
	private double squareHeight;
	private double squareWidth;
	private double screenWidth;
	private double screenHeight;
	public ArrayList<RectF> drawnSquares = new ArrayList<RectF>();
	
	/** simple constructor
	 * @param context
	 */
	public DrawBoard(Context context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//TODO remove, this is just for testing...
			ArrayList<Square> temp = new ArrayList<Square>();
			int x = 0;
			while(x < 40){
				x++;
				temp.add(new BasicSquare(10, 10));
			}
			setup(temp);
		
		//Must call to determine each squares size on device
		setRelative(canvas);
		
		setUpArray();
		
		//this can be called multiple times to change squares for monopolies in the for loop later
		Paint myColor = setColor(Color.BLUE);
		
		//draw all the squares for the board
		for(RectF element : drawnSquares){
			myColor = setColor(Color.BLUE);
			canvas.drawRoundRect(element, 25f, 25f, myColor);
			myColor = setBorder(Color.BLACK);
			canvas.drawRoundRect(element, 25f, 25f, myColor);
		}
	}
	
	/**
	 * Sets the size of each square relative to the screen size
	 * @param canvas the canvas do draw to
	 */
	private void setRelative(Canvas canvas){
		squareHeight = (screenHeight) / (numSquares + 1) - 1;
		squareWidth = screenWidth / (numSquares + 1) - 1;
	}
	
	/** Sets up the color for the squares
	 * @param color the color desired in integer form
	 * @return a Paint object of the color given
	 */
	private Paint setColor(int color){
		//create new color and fill it in
		Paint newColor = new Paint();
		newColor.setColor(color);
		newColor.setStyle(Paint.Style.FILL);
		newColor.setAntiAlias(true);
		return newColor;
	}
	
	/** Sets up the color for the squares border
	 * @param color the color of the desired border
	 * @return a Paint object of the given color for border only
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
	 * adds elements to drawnSquares ArrayList in the shape of a rectangle
	 */
	private void setUpArray(){
		float tempIntLeft = 0;
		float tempIntTop = 0;
		float tempIntRight = (float) squareWidth;
		float tempIntBottom =  (float) squareHeight;
		
		for(int i = 0; i < SIDES; i++){
			for(int x = 0; x < numSquares; x++){
				//move squares right when creating the top of the board
				if(i == 0){
					tempIntLeft += squareWidth + 1; 
					tempIntRight += squareWidth + 1;
				}
				//" " left " " " bottom " " " 
				if(i == 2){ 
					tempIntLeft -= squareWidth + 1; 
					tempIntRight -= squareWidth + 1;
				}
				//" " down " " " right " " " 
				if(i == 1){
					tempIntTop += squareHeight + 1;
					tempIntBottom += squareHeight + 1;
				}
				//" " up " " " left " " " 
				if(i == 3){ 
					tempIntTop -= squareHeight + 1; 
					tempIntBottom -= squareHeight + 1;
				}
				
				//create and add each Rect in respective position
				drawnSquares.add(new RectF(tempIntLeft, tempIntTop, tempIntRight, tempIntBottom));
			}
		}
	}
	
	/**
	 * Sets up the classes variables
	 * @param squares array to be drawn
	 */
	public void setup(ArrayList<Square> squares){
		numSquares = ((squares.size() / 4) - 1);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenWidth = w;
        screenHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }


	

}
