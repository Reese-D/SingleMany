package com.example.singlemany;

import android.content.Context;
import android.util.Log;

public class SpecialSquare extends Square {
	private int value;
	private String action;
	MainActivity c;
	private String Tag = "SpecialSquare";
	
	public SpecialSquare (String name, Context c){
		super.setName(name);
		super.typeId = 3;
		this.c = (MainActivity)c;
		value = (int) Math.floor(Math.random()*4);
		Log.i(Tag, "value is: " + value);
	}
	
	public void duAction(Player player) {
		
		switch(value)
		{
		case 0:
			player.addMoney(200);
			action = "You gained 200 dollars";
			break;
		case 1:
			player.payMoney(200);
			action = "You needed to pay taxes 200 dollars";
			break;
		case 2:
			player.setIsInJail(true);
			action = "You need to go to jail for one turn";
			c.moveToSquareType(4);
			break;
		case 3:
			action = "Go to Go square";
			c.moveToSquareType(2);
			break;
			
		}
	}

}
