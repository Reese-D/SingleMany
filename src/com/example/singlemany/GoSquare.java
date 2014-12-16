package com.example.singlemany;

public class GoSquare extends Square{
	
	public GoSquare (String name){
		super.setName(name);
		super.typeId = 2;
		
	}

	public void duAction(Player player) {
	player.addMoney(200);
}
}
