package com.example.singlemany;

import android.content.Context;

public class SpecialSquare extends Square {
	private int value = (int )(Math.random() * 4 + 1);
	private String action;
	MainActivity c;
	
	public SpecialSquare (String name, Context c){
		super.setName(name);
		super.typeId = 3;
		this.c = (MainActivity)c;
		
	}
	
	public void duAction(Player player) {
		switch(value)
		{
		case 1:
			player.addMoney(200);
			action = "You gained 200 dollars";
			c.makeToast(action, true);
			break;
		case 2:
			player.payMoney(200);
			action = "You needed to pay taxes 200 dollars";
			c.makeToast(action, true);
			break;
		case 3:
			player.setIsInJail(true);
			action = "You need to go to jail for one turn";
			c.makeToast(action, true);
			c.moveToSquareType(4);
			break;
		case 4:
			action = "Go to Go square";
			c.makeToast(action, true);
			c.moveToSquareType(2);
			break;
			
		}
	}

}
