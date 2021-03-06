package com.example.singlemany;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Square {
	protected String name;
	protected static int position;
	protected int typeId;
	protected float x;
	protected float y;
	//Add some variables to comply with mThread class
	protected Paint mPaint = new Paint();
	protected Paint mPaintRim = new Paint();
	protected RectF mRectF = new RectF();
	
	public Square(){
		//set paint defaults
		mPaint.setColor(Color.WHITE);
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
		mPaintRim.setStyle(Paint.Style.STROKE);
		mPaintRim.setStrokeWidth(2f);
		mPaintRim.setAntiAlias(true);
		mPaintRim.setAlpha(255);
	}
	public void duAction(Player player)
	{
		
	}
	
	public void setX(float amount){ 
		x = amount;
	}
	public void setY(float amount){ 
		y = amount;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
	
	public Paint getPaint() {
		return mPaint;
	}

	public String getName() {
		return name;
	}

	public RectF getRectF() {
		return mRectF;
	}

	public void setRectF(RectF rectF) {
		mRectF = rectF;
		
	}
	
	public void setPaintColor(int c){
		mPaint.setColor(c);
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public Paint getmPaintRim() {
		return mPaintRim;
	}
	public void setmPaintRim(Paint mPaintRim) {
		this.mPaintRim = mPaintRim;
	}
	public int getTypeId()
	{
		return typeId;
	}
}
