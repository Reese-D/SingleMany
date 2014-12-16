package com.example.singlemany;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.Button;

public class Connection extends Thread{
	
	//variables to interact with main activity
	protected MainActivity theMain;
	protected ArrayList<Player> players;
	protected Button roll;
	protected int myPlayerNumber;
	protected int currentPlayerNumber;
	
	//create variables to read and write from stream
	protected static final int ROLL_ACTION = 1;
	protected static final int END_TURN_ACTION = 2;
	protected static final int BUY_PROPERTY_ACTION = 3;
	protected static final int MOTION_EVENT = 4;
	protected static final int PLAYER_NUMBER = 5;

	//player identifier
	protected int whoAmI = 0;
	
	//variables for bluetooth connectivity
    protected BluetoothSocket mSocket;
    protected InputStream mInStream;
    protected OutputStream mOutStream;
    
    //DO NOT USE. only exists so it can be extended by children classes without deleting the other constructor
    public Connection(){
    	mSocket = null;
    	mOutStream = null;
    	mInStream = null;
    }
    
	
	public Connection(Context c, BluetoothSocket b){
		//Initialize components for main
		theMain = (MainActivity) c;
		players = theMain.players;
		roll = theMain.roll;
		myPlayerNumber = theMain.myPlayerNumber;
		currentPlayerNumber = theMain.currentPlayerNumber;
		
		//initialize sockets and streams
		mSocket = b;
		InputStream tmpIn = null;
        OutputStream tmpOut = null;
 
        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = b.getInputStream();
            tmpOut = b.getOutputStream();
        } catch (IOException e) { }
 
        mInStream = tmpIn;
        mOutStream = tmpOut;
	}
	
	protected void movePlayer(int r){
		theMain.roll(r);
	}
	
	protected void endTurn(){
		theMain.endTurn();
	}
	
	protected void buyProperty(){
		theMain.buyProperty();
	}
	
	protected void clickLocation(MotionEvent e){
		theMain.touch(e);
	}
	
	
	/* -------------------------------------------------------------------------------------------------------
	 * The following methods are for reading and writing to and from streams
	 * -------------------------------------------------------------------------------------------------------
	 */
	public void run() {
        byte[] buffer = new byte[7];  // buffer store for the stream
        int bytes; // bytes returned from read()
 
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mInStream.read(buffer);
                switch (bytes){
                case ROLL_ACTION:
                	movePlayer(buffer[1]);
                	break;
                case END_TURN_ACTION:
                	endTurn();
                	break;
                case BUY_PROPERTY_ACTION:
                	buyProperty();
                	break;
                case PLAYER_NUMBER:
                	whoAmI = buffer[1];
                case MOTION_EVENT:
                	int downTime = buffer[1];;
                	int eventTime = buffer[2];
                	int action = buffer[3];
                	int x = buffer[4];
                	int y = buffer[5];
                	int pressure = buffer[6];
                	
                	MotionEvent myEvent = MotionEvent.obtain(downTime, eventTime, action, x, y, pressure);
                	clickLocation(myEvent);
                	myEvent.recycle();
                	break;
                }
                	
                //TODO do something
            } catch (IOException e) {
                break;
            }
        }
    }
 
    /* Call this from the main activity to send data to the remote device */
	public void writeMotionEvent(MotionEvent e){
		long downTime = e.getDownTime();
		long eventTime =e.getEventTime();
		long action =e.getAction();
		float x =e.getX();
		float y =e.getY();
		float pressure =e.getPressure();
		
		byte[] bytes = new byte[]{MOTION_EVENT,(byte)downTime, (byte)eventTime, (byte)action, (byte)x, (byte)y
				, (byte)pressure};
		try {
            mOutStream.write(bytes);
        } catch (IOException exception) { }
	}
	
    /* Call this from the main activity to send data to the remote device */
	public void write(int bytes){
		try {
            mOutStream.write(bytes);
        } catch (IOException e) { }
	}
	
    /* Call this from the main activity to send data to the remote device */
	public void write(byte[] bytes){
		try {
            mOutStream.write(bytes);
        } catch (IOException e) { }
	}
 
    /* Call this from the main activity to shutdown the connection */
	public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) { }
    }
	
}
