package com.example.singlemany;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	manager m;
	
	//ArrayLists for the different objects we'll be dealing with
	ArrayList<Bitmap> playerBitmaps;
	ArrayList<Player> players;
	ArrayList<Square> squares;
	ArrayList<TextView> playersMoney;
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
	Square currentlySelectedSquare;

	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        //create a new manager instance, the logical manager of gameflow
        m = new manager();
        
        //instantiate some of our resources and arrays
    	players = m.playerArray;
        playerBitmaps = new ArrayList<Bitmap>();
        information = (TextView) findViewById(R.id.Info);
        gameBoard = (View) findViewById(R.id.Board);
        playersMoney = new ArrayList<TextView>();
        currentlySelectedSquare = null;
        
        //this will be the size of the font on the squares, designated in the dimen file
        textSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        
        //add the text views to the ArrayList for players cash to be displayed
        playersMoney.add((TextView) findViewById(R.id.player1Gold));
        playersMoney.add((TextView) findViewById(R.id.player2Gold));
        playersMoney.add((TextView) findViewById(R.id.player3Gold));
        playersMoney.add((TextView) findViewById(R.id.player4Gold));
        
        //TODO set to actual gold only set 0 for players not in game
        for(TextView x : playersMoney){
        	x.setText("Gold: 0");
        }
        
        //setup buttons
        Button roll = (Button) findViewById(R.id.roll_button);
        Button endTurn = (Button) findViewById(R.id.end_turn_button);
        Button buyProperty = (Button) findViewById(R.id.buy_property);
        
   	 	//set button listener
        roll.setOnClickListener(setButtonListener());
        endTurn.setOnClickListener(setEndTurnButtonListener());
        buyProperty.setOnClickListener(setBuyPropertyListener());
        
        //TODO remove, creates some squares just for testing the application
        squares = m.boardArrayList;
        
        //add the bitmaps to array
        	//TODO add more cars and add them to the list, right now we only have red car
        playerBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.red_car));

        //set some defaults to the board to make it look nicer
        ((Board) gameBoard).setTextSize(textSize);
    	information.setTextColor(Color.WHITE);

    	//TODO change if the player array or square array is conceived differently
    	((Board) gameBoard).setupBoard(players, squares);
    	

        //TODO add 3 more imageViews for the 3 other players
    	
        //set the imageView from our xml as the red car
        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageResource(R.drawable.red_car);
        
        //find screen width and height to set car size
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        imageWidth = displayMetrics.widthPixels;
        imageHeight = displayMetrics.heightPixels;
        //scale it down to a preset value found through experimentation
        imageHeight /= IMAGESCALE;
        imageWidth /= IMAGESCALE;
        //set the imageView to this newly found size
        iv.getLayoutParams().height = imageHeight;
        iv.getLayoutParams().width = imageWidth;
        //set the image of the car in the first square of the array
        iv.setX(squares.get(0).getX());
        iv.setY(squares.get(0).getY());
        
        
    	//TODO create a method to get players from manager class and pass it down, then delete this
    	//for now just creates two players, both with red cars, but one won't be drawn because the
    	//change player function isn't implemented

    	
    	//set the image for each TODO add the other three players
    	players.get(0).setImageView(iv);
    	

    }
    
    private OnClickListener setBuyPropertyListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				//buy currently selected square
				if(currentlySelectedSquare != null)
					m.Buy(currentlySelectedSquare);
				//TODO change part of the squares color to the players color
				for(Player c :players){
					for(Square s : c.getProperties()){
						s.setPaintColor(c.getColor);
					}
				}
			}
		};
	}

	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * @return returns a listener for the EndTurn button
     */
    public View.OnClickListener setEndTurnButtonListener(){
    	return new OnClickListener(){
	    	@Override
			public void onClick(View v) {
	    		m.changePlayer();
	    		currentPlayer = m.currentPlayer;
	    	}
    	};
    }
    
    /**
     * @return sets a listener for the ROLL button
     */
    public View.OnClickListener setButtonListener(){
    	return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentPlayer= m.currentPlayer;
				rolled = currentPlayer.throwDice();
				currentPosition = currentPlayer.getPositionInBoard();

				final Runnable r = new Runnable()
				{
				    public void run() 
				    {
				    	//TODO change from iv to the current players ImageView
						Square currentSquare;
						currentPlayer.SetPositionInBoard((currentPosition + 1) % squares.size() );
						currentPosition = currentPlayer.getPositionInBoard();
						currentSquare = squares.get(currentPosition);
						
						//animate image view
						iv.animate().translationX(currentSquare.getX() - (imageWidth/2));
						iv.animate().translationY(currentSquare.getY() - (imageHeight/2));
						iv.animate().setDuration(MOVETIME);
						//long oldTime = SystemClock.elapsedRealtime();
						iv.animate().start();
						setText("Car at position: " + (currentSquare.name));
						
						//players may exchange cash so update all players money
						for(int i = 0; i < players.size(); i++){
							//for ever player get their cash and set the corresponding
							//gold text to the amount
							playersMoney.get(i)
								.setText("Player"+ Integer.toString(i) + " Gold: " 
							+ Double.toString(players.get(i).getMoney()));
						}
				    }
				};
				int delay = 0;
				
				//call each move 1 at a time with a delay between them
				while(rolled > 0){
					rolled--;
					iv.postDelayed(r, delay);
					delay += MOVETIME;
				}
				
				//have the manager move player so it updates
				m.movePlayer();
			}
		};
    }
    
   
    /**
     * @param string new text for the center of the screen
     * @param color change the color of the text if needed
     */
    public void setText(String string, int color){
    	information.setTextColor(color);
     	information.setText(string);
    }
    
    /**
     * @param string the amount of gold to display
     * @param index the players index in the array to be updated
     */
    public void setPlayerGold(String string, int index){
    	playersMoney.get(index).setText(string);
    }
    
    /** Uses the current color for the text
     * @param s string for new text in center of the screen
     */
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



	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//get the x and y of the touch
		float x = event.getX();
		float y = event.getY();
		Square temp = currentlySelectedSquare;
		
		//check if the x y is within one of the squares
		for(Square element :squares){
			if(element.getRectF().contains(x, y)){
				currentlySelectedSquare = element;
				setText("selected square: " + (element.name));
				
				//TODO remove red and get alpha to work
				element.getPaint().setAlpha(50);
				element.getPaint().setColor(Color.RED);
				if(temp != null)
					temp.getPaint().setColor(Color.BLUE);
					temp.getPaint().setAlpha(255);
				return super.onTouchEvent(event);
			}
		}
		return super.onTouchEvent(event);

	}
}
