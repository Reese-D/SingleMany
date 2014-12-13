package com.example.singlemany;

import java.util.UUID;

import android.bluetooth.BluetoothSocket;

public class AccessGlobal{
	private static BluetoothSocket mSocket;
	private static AccessGlobal mGlobalSingleton;
	public static final int CONNECTED_AS_CLIENT = 45;
    public static UUID mUUID = UUID.fromString("5ee597ee-3269-4840-a741-8539ee94859a");
    public static String returnedDevices = "returned";
	private AccessGlobal(){
		mSocket = null;
	}
	
	public static AccessGlobal create(){
		if(mGlobalSingleton == null){
			mGlobalSingleton = new AccessGlobal();
		}
		return mGlobalSingleton;
	}
	
	public static BluetoothSocket getSocket(){
		return mSocket;
	}

	public static void setSocket(BluetoothSocket b){
		mSocket = b;
	}
}
