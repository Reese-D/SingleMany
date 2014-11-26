package com.example.singlemany;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

	ArrayList<Bitmap> playerBitmaps;
	int textSize;
	View gameBoard;
	CharSequence c = "i am the char sequence";
	TextView information;
	String s;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //get resources
        textSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        playerBitmaps = new ArrayList<Bitmap>();
        information = (TextView) findViewById(R.id.Info);
        gameBoard = (View) findViewById(R.id.Board);
        
        //add the bitmaps to array
        	//TODO add more cars and add them to the list
        playerBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.red_car));

        //set some defaults
        ((Board) gameBoard).setTextSize(textSize);
    	information.setTextColor(Color.WHITE);
    	
    	//TODO create a method to get players from manager class and pass it down, then delete this
    	ArrayList<Player> temp = new ArrayList<Player>();
    	
    	Player p = new Player();
    	p.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_car));
    	temp.add(p);
    	//Temporary, we'll need a list later, TODO put this in a setup() method.
    	((Board) gameBoard).setupBoard(temp, null);
    }

    public void getPlayerBitmap(int index) throws IndexOutOfBoundsException{
    	if(index > playerBitmaps.size() -1){
    		throw new IndexOutOfBoundsException("There are no images at the specified index");
    	}
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void setText(String string, int color){
    	information.setTextColor(color);
     	information.setText(string);
    }
    
    public void setText(String s){
     	information.setText(s);
    }

    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//TODO add on touch events
		//float x = event.getX();
		//float y = event.getY();
		
		return super.onTouchEvent(event);

	}
    
}
