package com.example.singlemany;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	ArrayList<Bitmap> playerBitmaps;
	ArrayList<Player> players;
	ArrayList<Square> squares;
	int textSize;
	View gameBoard;
	CharSequence c = "i am the char sequence";
	TextView information;
	String s;
	ImageView iv;
	int imageWidth, imageHeight;
	final int IMAGESCALE = 15;
	final int MOVETIME = 500;
	int rolled, currentPosition;
	Player currentPlayer;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //get resources
        FrameLayout myFLayout = (FrameLayout) findViewById(R.id.Layout1);
        textSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        playerBitmaps = new ArrayList<Bitmap>();
        information = (TextView) findViewById(R.id.Info);
        gameBoard = (View) findViewById(R.id.Board);
        Button roll = (Button) findViewById(R.id.roll_button);

        
        //TODO remove
    	squares = new ArrayList<Square>();
    	for(int x = 0; x < 40; x++)
    		squares.add(new BasicSquare(10, 10, "test\nNameof Square"));
        
       
        
        //add the bitmaps to array
        	//TODO add more cars and add them to the list
        playerBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.red_car));

        //set some defaults
        ((Board) gameBoard).setTextSize(textSize);
    	information.setTextColor(Color.WHITE);
    	
    	//TODO create a method to get players from manager class and pass it down, then delete this
    	players = new ArrayList<Player>();
    	
    	Player p = new Player();
    	p.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_car));
    	players.add(p);
    	//Temporary, we'll need a list later, TODO put this in a setup() method.
    	((Board) gameBoard).setupBoard(players, squares);
    	
    	 //set roll listener
        roll.setOnClickListener(setButtonListener());
        
        
        //set up simple animation
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageResource(R.drawable.red_car);
        
        //find screen width and height to set car size
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        imageWidth = displayMetrics.widthPixels;
        imageHeight = displayMetrics.heightPixels;
        imageHeight /= IMAGESCALE;
        imageWidth /= IMAGESCALE;
        iv.getLayoutParams().height = imageHeight;
        iv.getLayoutParams().width = imageWidth;
        
        iv.setX(squares.get(0).getX());
        iv.setY(squares.get(0).getY());
        
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
    
    public View.OnClickListener setButtonListener(){
    	return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPlayer= players.get(0);
				rolled = currentPlayer.throwDice();
				currentPosition = currentPlayer.getPositionInBoard();



				final Runnable r = new Runnable()
				{
				    public void run() 
				    {
						Square currentSquare;
						//while(rolled > 0){
							//rolled --;
							currentPlayer.SetPositionInBoard((currentPosition + 1) % squares.size() );
							currentPosition = currentPlayer.getPositionInBoard();
							currentSquare = squares.get(currentPosition);
							iv.animate().translationX(currentSquare.getX() - (imageWidth/2));
							iv.animate().translationY(currentSquare.getY() - (imageHeight/2));
							iv.animate().setDuration(MOVETIME);
							long oldTime = SystemClock.elapsedRealtime();
							iv.animate().start();
/*							TranslateAnimation anim = new TranslateAnimation(
							iv.getX(), currentSquare.getX() - (imageWidth/2),
							iv.getY(), currentSquare.getY() - (imageHeight/2)) ;
						    anim.setDuration(1000);
						    iv.startAnimation(anim);*/
						    //setText( "is animated: " + iv.getAnimation());
							setText("Car at position: " + (currentPosition));
						//}
				    }
				};
				//r.run();
				int delay = 0;
				while(rolled > 0){
					rolled--;
					iv.postDelayed(r, delay);
					delay += MOVETIME;
				}

				
				
				/*final Runnable b = new Runnable(){
					Runnable br = r;
					@Override
					public void run() {
						br.run();
						try {
							this.wait(MOVETIME);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				};
				b.run();*/

			}
		};
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
