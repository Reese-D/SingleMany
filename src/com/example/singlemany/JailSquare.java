package com.example.singlemany;

public class JailSquare extends Square {
	
	public JailSquare (String name){
		super.setName(name);
		super.typeId = 4;
		
	}
	public void duAction(Player player) {
		player.setIsInJail(true);
	}

}
