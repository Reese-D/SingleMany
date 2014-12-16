package com.example.singlemany;

public class SpecialSquare extends Square {
	private int value = (int )(Math.random() * 3 + 1);
	private String action;
	
	public SpecialSquare (String name){
		super.setName(name);
		super.typeId = 3;
		
	}
	
	public void duAction(Player player) {
		switch(value)
		{
		case 1:
			player.addMoney(200);
			action = "You gained 200 dollars";
			break;
		case 2:
			player.payMoney(200);
			action = "You needed to pay taxes 200 dollars";
			break;
		case 3:
			player.setIsInJail(true);
			action = "You need to go to jail for one turn";
			break;
			
		}
	}

}
