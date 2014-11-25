package com.example.singlemany;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

	int textSize;
	View gameBoard;
	CharSequence c = "i am the char sequence";
	TextView information;
	String s;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        
        //gameBoard = new DrawBoard(this);
        gameBoard = (View) findViewById(R.id.Board);
        ((Board) gameBoard).setTextSize(textSize);
        information = (TextView) findViewById(R.id.Info);
        
        //set white as default text color
    	information.setTextColor(Color.WHITE);
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
