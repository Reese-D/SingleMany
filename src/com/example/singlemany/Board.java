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
    public Board(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
        thread = new mThread(holder, context);


       	
       	//call setup
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
		