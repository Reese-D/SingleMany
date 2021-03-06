package com.example.singlemany;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	manager m;
	
	//ArrayLists for the different objects we'll be dealing with
	public ArrayList<Bitmap> playerBitmaps;
	public ArrayList<Player> players;
	public ArrayList<Square> squares;
	public ArrayList<TextView> playersMoney;
	int textSize;
	View gameBoard;
	CharSequence c = "i am the char sequence";
	public TextView information;
	public String s;
	ImageView iv;
	ImageView iv2;
	int imageWidth, imageHeight;
	final int IMAGESCALE = 15;
	final int MOVETIME = 500;
	int rolled, currentPosition;
	public volatile Player currentPlayer;
	public Square currentlySelectedSquare;
	BluetoothDevice mBluetoothDevice;
	private final int bluetoothRequestCode = 1241;
	View myView;
	public int Counter = 0;
	ArrayAdapter<String> mArrayAdapter;
	PopupWindow mPopup;
	public int myPlayerNumber;

	public Button roll;

	public Button endTurn;

	public Button buyProperty;

	public int currentPlayerNumber;
	
	Intent mIntent;
	public volatile Square currentSquare;
	Connection myConnection;

	private BluetoothSocket mBluetoothSocket;

	private String Tag = "MainActivity.java";

	private TextView information1;

	private TextView information2;

	private TextView information3;

	private TextView information4;

	private TextView information5;

	private boolean killed = false;
	
    protected void onCreate(Bundle savedInstanceState) {
        myView = getLayoutInflater().inflate(R.layout.activity_main, null);
        super.onCreate(savedInstanceState);
        setContentView(myView);

        //mIntent = new Intent(this, Bluetooth.class);

        

        //create a new manager instance, the logical manager of gameflow
        m = new manager(this);
        
        //instantiate some of our resources and arrays
        squares = m.boardArrayList;
    	players = m.playerArray;
        playerBitmaps = new ArrayList<Bitmap>();
        information = (TextView) findViewById(R.id.Info);
        information1 = (TextView) findViewById(R.id.Info1);
        information2 = (TextView) findViewById(R.id.Info2);
        information3 = (TextView) findViewById(R.id.Info3);
        information4 = (TextView) findViewById(R.id.Info4);
        information5 = (TextView) findViewById(R.id.Info5);
        information.setTextColor(Color.WHITE);
        information1.setTextColor(Color.WHITE);
        information2.setTextColor(Color.WHITE);
        information3.setTextColor(Color.WHITE);
        information4.setTextColor(Color.WHITE);
        information5.setTextColor(Color.WHITE);
        
        gameBoard = (View) findViewById(R.id.Board);
        playersMoney = new ArrayList<TextView>();
        currentlySelectedSquare = null;
        currentPlayer = m.currentPlayer;
        
        //this will be the size of the font on the squares, designated in the dimen file
        textSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);
        
        //add the text views to the ArrayList for players cash to be displayed
        playersMoney.add((TextView) findViewById(R.id.player1Gold));
        playersMoney.add((TextView) findViewById(R.id.player2Gold));
        playersMoney.get(0).setTextColor(Color.WHITE);
        playersMoney.get(1).setTextColor(Color.WHITE);
        
        //TODO set to actual gold only set 0 for players not in game
        for(TextView x : playersMoney){
        	x.setText("Gold: 0");
        }
        //testing
        
        //setup buttons
        roll = (Button) findViewById(R.id.roll_button);
        endTurn = (Button) findViewById(R.id.end_turn_button);
        buyProperty = (Button) findViewById(R.id.buy_property);
        
   	 	//set button listener
        roll.setOnClickListener(setButtonListener());
        endTurn.setOnClickListener(setEndTurnButtonListener());
        buyProperty.setOnClickListener(setBuyPropertyListener());
        
        
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
        iv2 = (ImageView) findViewById(R.id.imageView2);
        iv.setImageResource(R.drawable.red_car);
        iv2.setImageResource(R.drawable.blue_car);
        
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
        iv2.getLayoutParams().height = imageHeight;
        iv2.getLayoutParams().width = imageWidth;
        //set the image of the car in the first square of the array
        iv.setX(squares.get(0).x);
        iv.setY(squares.get(0).y);
        iv2.setX(squares.get(0).x);
        iv2.setY(squares.get(0).y);

    	//set the image for each TODO add the other three players
    	players.get(0).setImageView(iv);
    	players.get(1).setImageView(iv2);

        //startActivityForResult(mIntent, bluetoothRequestCode);

    }
    
    /** Listener for BUY PROPERTY button
     * @return
     */
    private OnClickListener setBuyPropertyListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!currentPlayer.getIsInJail())
					buyProperty();
				else{
					makeToast("You can't buy properties while in jail", false);
				}
			}
		};
	}
    
    public void buyProperty(){
		//buy currently selected square
		if(currentlySelectedSquare != null){
			Log.i(Tag , "called manager to purchase property");
			m.Buy(currentlySelectedSquare);
		//set paint border to players color
			for(Player p: players)
				for(Square s: p.getProperties())
					s.setmPaintRim(p.getmPaint());
		}
		updateGold();
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
	    		if(currentPlayer.getHasThrownDice())
	    			endTurn();
	    		else{
	    			if(currentPlayer.getIsInJail())
	    				endTurn();
	    			else{
	    				Toast.makeText(v.getContext(), "you havn't rolled yet...", Toast.LENGTH_SHORT).show();
	    			}
	    		}
	    		
	    		if(currentPlayer.getMoney() < 0){
	    			doRestart(myView.getContext());
	    		}
	    	}
    	};
    }
    
    public void endTurn(){
    	updateGold();
    	Log.i(Tag, "end turn button clicked, manager changePlayer called");
    	m.changePlayer();
		currentPlayer = m.currentPlayer;
    }
    
    public void updateGold(){
		//players may exchange cash so update all players money
		for(int i = 0; i < players.size(); i++){
			//set gold for players in text
			setPlayerGold((int) players.get(i).getMoney(), i);
		}
    }
    
    /**
     * @return sets a listener for the ROLL button
     */
    public View.OnClickListener setButtonListener(){
    	return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!currentPlayer.getIsInJail()){
					roll(currentPlayer.throwDice());
				}else{
					makeToast("You're in Jail, you can't move or make purchases", false);
				}
			}
		};
    }
    
    
    
    /*@Override
	protected void onStop() {
		super.onPause();
		((Board) gameBoard).killThread();
		killed  = true;
	}
    
    

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(gameBoard != null && killed){
			((Board) gameBoard).restartThread();
			((Board) gameBoard).setupBoard(players, squares);
			killed = false;
		}
	}*/

	public void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity 
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called. 
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e(Tag, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(Tag, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(Tag, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(Tag, "Was not able to restart application");
        }
    }

	public void moveToSquareType(int typeId){
		currentPlayer= m.currentPlayer;
		currentPosition = currentPlayer.getPositionInBoard();
		endTurn.setOnClickListener(null);

		final Runnable r = new Runnable()
		{
		    public void run() 
		    { 
				endTurn.setOnClickListener(null);
		    	//move player one and set that spot as current position and current square
				currentPlayer.SetPositionInBoard((currentPosition + 1) % squares.size() );
				currentPosition = currentPlayer.getPositionInBoard();
				currentSquare = squares.get(currentPosition);
				Log.i(Tag, "currentSquare is: " + currentSquare);
				//animate image view
				currentPlayer.getImageView().animate().translationX(currentSquare.getX() - (imageWidth/2));
				currentPlayer.getImageView().animate().translationY(currentSquare.getY() - (imageHeight/2));
				currentPlayer.getImageView().animate().setDuration(MOVETIME);
				//long oldTime = SystemClock.elapsedRealtime();
				currentPlayer.getImageView().animate().start();
				// TODO setText("Car at position: " + (currentSquare.name));
				
				updateGold();
		    }
		};
		int delay = 0;
		final Runnable r2d2 = new Runnable(){
			
			@Override
			public void run() {
				if(currentSquare != null){
					endTurn.setOnClickListener(null);
					Log.i(Tag, "called DuAction on current square");
					currentSquare.duAction(currentPlayer);
				}
				endTurn.setOnClickListener(setEndTurnButtonListener());
				currentPlayer.setPosition(currentSquare);
				updateGold();
			}
		};
		//call each move 1 at a time with a delay between them
		Log.i(Tag, "hasThrownDice value is: " + currentPlayer.getHasThrownDice());
			Log.d(Tag, "Got to stage 2");
			Square tempSquare = squares.get(currentPosition);
			int count = 0;
			while(tempSquare.typeId != typeId){
				count++;
				tempSquare = squares.get((currentPosition + count) % squares.size());
				rolled--;
				iv.postDelayed(r, delay);
				delay += MOVETIME/2;
				//have the manager move player so it updates
				if(currentSquare != null && currentSquare.typeId == 2){
					currentSquare.duAction(currentPlayer);
				}
			}
			iv.postDelayed(r2d2, delay + MOVETIME*2);
			updateGold();
    }
    
    
    
    public void roll(int rolled){
		currentPlayer= m.currentPlayer;
		currentPosition = currentPlayer.getPositionInBoard();
		final Runnable r = new Runnable()
		{
		    public void run() 
		    { 
		    	endTurn.setOnClickListener(null);
		    	//move player one and set that spot as current position and current square
				currentPlayer.SetPositionInBoard((currentPosition + 1) % squares.size() );
				currentPosition = currentPlayer.getPositionInBoard();
				currentSquare = squares.get(currentPosition);
				Log.i(Tag, "currentSquare is: " + currentSquare);

				//animate image view
				currentPlayer.getImageView().animate().translationX(currentSquare.getX() - (imageWidth/2));
				currentPlayer.getImageView().animate().translationY(currentSquare.getY() - (imageHeight/2));
				currentPlayer.getImageView().animate().setDuration(MOVETIME);
				//long oldTime = SystemClock.elapsedRealtime();
				currentPlayer.getImageView().animate().start();
				// TODO setText("Car at position: " + (currentSquare.name));
				updateGold();
		    }
		};
		int delay = 0;
		final Runnable r2d2 = new Runnable(){
			
			@Override
			public void run() {
				if(currentSquare != null){
					Log.i(Tag, "called DuAction on current square");
					currentSquare.duAction(currentPlayer);
					endTurn.setOnClickListener(null);
				}
				endTurn.setOnClickListener(setEndTurnButtonListener());
				currentPlayer.setPosition(currentSquare);
				updateGold();
			}
		};
		//call each move 1 at a time with a delay between them
		Log.i(Tag, "hasThrownDice value is: " + currentPlayer.getHasThrownDice());
		if(!currentPlayer.getHasThrownDice()){
			currentPlayer.setHasThrownDice(true);
			while(rolled > 0){
				rolled--;
				iv.postDelayed(r, delay);
				delay += MOVETIME;
				//have the manager move player so it updates
				if(currentSquare != null && currentSquare.typeId == 2){
					currentSquare.duAction(currentPlayer);
				}
			}
			iv.postDelayed(r2d2, delay);
			updateGold();
		}
		

    }
    
    public void makeToast(String s, boolean upForLongTime){
    	int i;
    	if (upForLongTime)
    		i = Toast.LENGTH_LONG;
    	else{
    		i = Toast.LENGTH_SHORT;
    	}
    
		Toast toast = Toast.makeText(this, s, i);
		toast.show();
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
    public void setPlayerGold(int gold, int index){
    	playersMoney.get(index).setText("Player"+ Integer.toString(index) +" Gold " + gold);
    }
    
    /** Uses the current color for the text
     * @param s string for new text in center of the screen
     */
    public void setText0(String s){
     	information.setText(s);
    }
    public void setText1(String s){
     	information1.setText(s);
    }
    public void setText2(String s){
     	information2.setText(s);
    }
    public void setText3(String s){
     	information3.setText(s);
    }
    public void setText4(String s){
     	information4.setText(s);
    }
    public void setText5(String s){
     	information5.setText(s);
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
		if(myPlayerNumber == currentPlayerNumber && event.getAction() == MotionEvent.ACTION_DOWN)
			touch(event);
		return super.onTouchEvent(event);
	}
	
	public void touch(MotionEvent event){
		//get the x and y of the touch (absolute not relative)
				float x = event.getX();
				float y = event.getY();
				Square temp = currentlySelectedSquare;
				
				//check if the x y is within one of the squares
				for(Square element :squares){
					if(gameBoard == null)
						Log.e(Tag, "Gameboard was null in touch event!");
					if(gameBoard != null && element.getRectF().contains(x,y 
							- ((Board)gameBoard).getSquareHeight())){
						currentlySelectedSquare = element;
						if(currentlySelectedSquare.getTypeId() == 2 || currentlySelectedSquare.getTypeId() == 3 || currentlySelectedSquare.getTypeId() == 4)
						{
							setText0("selected square: " + (currentlySelectedSquare.name));
							setText1("");
							setText2("");
							setText3("");
							setText4("");
							setText5("");
						}
						else
						{
							setText0("selected square: " + (currentlySelectedSquare.name));
							setText1("Property's purchase price: " + (((BasicSquare) currentlySelectedSquare).getPurchasePrice()));
							setText2("Property's house price: " + (((BasicSquare) currentlySelectedSquare).getHousePrice()));
							setText3("Property's hotel price: " + (((BasicSquare) currentlySelectedSquare).getHotelPrice()));
							setText4("Property has  " + (((BasicSquare) currentlySelectedSquare).getHouses()) + " and has hotel " + (((BasicSquare) currentlySelectedSquare).getHasHotel()));
							setText5("Property's rent is: " + (((BasicSquare) currentlySelectedSquare).getBaseRent()));
							
						}
						
						//TODO remove red and get alpha to work
						currentlySelectedSquare.getPaint().setAlpha(50);
						currentlySelectedSquare.getPaint().setColor(Color.GRAY);
						if(temp != null){
							temp.getPaint().setColor(Color.WHITE);
							temp.getPaint().setAlpha(255);
						}
						return;
					}
				}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == bluetoothRequestCode) {
	        if(resultCode == RESULT_OK){
	        	myConnection = new Connection (this, AccessGlobal.getSocket());
	        	
	        }
	        if (resultCode == RESULT_CANCELED) {
	            startActivityForResult(mIntent, bluetoothRequestCode);
	        }
	        if(resultCode == AccessGlobal.CONNECTED_AS_CLIENT){
	        	myPlayerNumber = 1;
	        	
	        	//connect as client to server
	        	AccessGlobal.create();
	        	myConnection = new Connection (this, AccessGlobal.getSocket());
	        }
	    }
	}
	
}
