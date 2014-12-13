package com.example.singlemany;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Bluetooth extends Activity{
	int REQUEST_ENABLE_BT = 5;
    private String EXTRA_DEVICE_ADDRESS = "com.example.singlemany.ExtraDevices";
    
	
	//Create the array and strings to be displayed in our ListView
	BluetoothAdapter mBluetoothAdapter;
	ArrayAdapter<String> mArrayAdapter;
	ArrayList<String> selected;
	
	//intent to send to start the game
	Intent finishAndCall;
	
	//buttons
	Button mScanButton, mFinishButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
        //initialize array to see which devices are selected (using mac address)
        selected = new ArrayList<String>();

		Thread m = new Thread(makeRunnable(this));
		m.start();
        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);
        
        //initialize intent
        finishAndCall = new Intent();
        
        //Initialize the button to perform device discovery
        mScanButton = (Button) findViewById(R.id.scanButton);
        mScanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	scanForDevices();
            	//button dissapears so user can't mash it while it's scanning
                v.setVisibility(View.GONE);
            }
        });
       
        
        //initialize finish button
		mFinishButton = (Button) findViewById(R.id.finishButton);
		mFinishButton.setOnClickListener(finishButtonListener);
        
		//setup adapter and add it the list view
		mArrayAdapter = new ArrayAdapter<String>(this, R.layout.devices);
		ListView mListView = (ListView) findViewById(R.id.mListView);
		mListView.setOnItemClickListener(mDeviceClickListener);
		mListView.setAdapter(mArrayAdapter);
		
		//get the local bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		//Try to enable bluetooth
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth TODO handle
		}else{
			if (!mBluetoothAdapter.isEnabled()) {
			    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
		
		//allow us to be discovered by other devices
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(discoverableIntent);
		

		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); //TODO Don't forget to unregister during onDestroy
		

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        

	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		//don't worry about request code since we only ever request one intent
		if(resultCode == RESULT_OK){
			//get list of paired devices, if there arn't any start up discovery
			//The user can use the scan button if they feel the need to search again
			if(!queryPairedDevices()) mBluetoothAdapter.startDiscovery();
		}else if(resultCode == RESULT_CANCELED){
			//TODO handle error or user pressed no to bluetooth
		}
	}
	
	
	
	private void scanForDevices(){
		Toast toast = Toast.makeText(this, "Scanning for devices...", Toast.LENGTH_LONG);
		toast.show();
		
		 // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
        	mBluetoothAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
	}
	
	
	
	private boolean queryPairedDevices(){
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    }
		    return true;
		}else{
			return false;
		}
	}
	
	
	
	 @Override
	    protected void onDestroy() {
	        super.onDestroy();

	        // Make sure we're not doing discovery anymore
	        if (mBluetoothAdapter != null) {
	            mBluetoothAdapter.cancelDiscovery();
	        }

	        // Unregister broadcast listeners
	        this.unregisterReceiver(mReceiver);
	    }
	 
	 private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice
                		.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            // When discovery is finished, let the user know
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            	String toastText = "Finished devices scan";
                if (mArrayAdapter.getCount() == 0) {
                	toastText += ", No devices were found";
                }
                Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        		toast.show();
        		
        		//button re-appears so user can scan again
        		mScanButton.setVisibility(View.VISIBLE);
            }
        }
    };
    // The on-click listener for all devices in the ListView
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

			

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            
            //select object if already selected otherwise deselect it
            if(!selected.contains(address)){
            	v.setBackgroundColor(Color.LTGRAY);
            	selected.add(address);
            }else{
            	v.setBackgroundColor(Color.TRANSPARENT);
            	selected.remove(address);
            }
        }
    };
    
    private  OnClickListener finishButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
            // Cancel discovery because it's costly and we're about to connect
            mBluetoothAdapter.cancelDiscovery();
            // Set result and finish this Activity
            
            //get all the selected bluetooth devices and add them to return intent
            for(String i: selected){
            	finishAndCall.putExtra(AccessGlobal.returnedDevices
            			, mBluetoothAdapter.getRemoteDevice(i));
            }

            //set our result and return
            setResult(Activity.RESULT_OK, finishAndCall);
            finish();
		}
    };
    
    /*
     * -------------------------------------------------------------------------------------------------------
     * Thread that will handle other devices requestion connections
     * -------------------------------------------------------------------------------------------------------
     */
    private Runnable makeRunnable(final Context c){
    	return new Runnable(){


    	    private String socketString = "com.example.SingleMany.Bluetooth";
    	    private BluetoothServerSocket mSocket;
    	    boolean constructed = false;
	        BluetoothSocket socket = null;
	        
    	    private void constructor(){
    	    	BluetoothServerSocket tmp = null;
    	    	try {
    				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(socketString,AccessGlobal
    						.mUUID);
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    	    	mSocket = tmp;
    	    }
    	    
    	    @Override
    	    public void run() {
    	    	if(!constructed){
    	    		constructor();
    	    		constructed = true;
    	    	}

    	        // Keep listening until exception occurs or a socket is returned
    	        while (true) {
    	            try {
    	                socket = mSocket.accept();
    	            } catch (IOException e) {
    	                break;
    	            }
    	            // If a connection was accepted
    	            if (socket != null) {
    	                //Create global instance of socket so other classes can obtain it
    	            	AccessGlobal.create();
    	            	AccessGlobal.setSocket(socket);
    	                try {
    						mSocket.close();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    	                
    	                //Open a dialog box saying a user wants to connect
    	                AlertDialog.Builder alertDialogBuilder 
    	                		= new AlertDialog.Builder(c);
    	                alertDialogBuilder.setTitle("Connection Found");
    	                alertDialogBuilder.setMessage("the device: " 
    	                + socket.getRemoteDevice().getAddress() + "named: "
    	                + socket.getRemoteDevice().getName() + " wishes to connect"
    	                + ", would you like to connect with them?");
    	                alertDialogBuilder.setPositiveButton("Accept", PositiveListener());
    	                alertDialogBuilder.setNegativeButton("Decline", NegativeListener());
    	                break;
    	            }
    	        }
    	    }
    		
    	};
    }
    
    private DialogInterface.OnClickListener PositiveListener(){
		return new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(AccessGlobal.CONNECTED_AS_CLIENT);
				finish();
				
			}
		};
   
    }
    
    private DialogInterface.OnClickListener NegativeListener(){
		return new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO finish
			}
		};
   
    }
    
    

}
