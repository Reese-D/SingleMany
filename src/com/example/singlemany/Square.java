package com.example.singlemany;

import android.graphics.Paint;
import android.graphics.RectF;

public class Square {
	protected String name;
	protected static int position;
	protected static int typeId;
	//Add some variables to comply with mThread class
	protected Paint mPaint = new Paint();
	protected RectF mRectF = new RectF();
	public void duAction(Player player)
	{
		
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
}
