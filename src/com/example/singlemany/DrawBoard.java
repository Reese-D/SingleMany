package com.example.singlemany;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	public ArrayList<Rect> drawnSquares;
	
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
		
		//Must call to determine each squares size on device
		setRelative(canvas);
		
		setUpArray();
		
		//this can be called multiple times to change squares for monopolies in the for loop later
		Paint myColor = setColor(Color.BLUE);
		
		//draw all the squares for the board
		for(Rect element : drawnSquares){
			canvas.drawRect(element, myColor);
		}
	}
	
	
	/**
	 * Sets the size of each square relative to the screen size
	 * @param canvas the canvas do draw to
	 */
	private void setRelative(Canvas canvas){
		squareHeight = canvas.getHeight() / numSquares;
		squareWidth = canvas.getWidth() / numSquares;
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
		return newColor;
	}
	
	
	/**
	 * adds elements to drawnSquares ArrayList in the shape of a rectangle
	 */
	private void setUpArray(){
		int tempIntLeft = 0;
		int tempIntTop = 0;
		int tempIntRight = (int) squareWidth;
		int tempIntBottom = (int) squareHeight;;
		
		for(int i = 0; i < SIDES; i++){
			//move squares right when creating the top of the board
			if(i == 0) tempIntLeft += squareWidth; tempIntRight += squareWidth;
			//" " left " " " bottom " " " 
			if(i == 2) tempIntLeft -= squareWidth; tempIntRight -= squareWidth;
			for(int x = 0; x < numSquares; x++){
				//" " down " " " right " " " 
				if(x == 1) tempIntTop += squareHeight; tempIntBottom += squareHeight;
				//" " up " " " left " " " 
				if(x == 3) tempIntTop -= squareHeight; tempIntBottom -= squareHeight;
				
				//create and add each Rect in respective position
				drawnSquares.add(new Rect(tempIntLeft, tempIntTop, tempIntRight, tempIntBottom));
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


	

}
