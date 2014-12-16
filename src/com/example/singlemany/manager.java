package com.example.singlemany;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class manager {

MainActivity c;
Square[] boardArray;
ArrayList<Player> playerArray;

/*playerArray[0] = player1;
playerArray[1] = player2;*/

Player currentPlayer;

//GoSquare square1;
private GoSquare square1; //TODO change to gosquare
private BasicSquare square2;
private SpecialSquare square3;
private BasicSquare square4;
private SpecialSquare square5;
private SpecialSquare square6;
private BasicSquare square7;
private JailSquare square8;
private BasicSquare square9;
private BasicSquare square10;
private SpecialSquare square11;
private BasicSquare square12;
private SpecialSquare square13;
private BasicSquare square14;
private BasicSquare square15;
private SpecialSquare square16;
private BasicSquare square17;
private SpecialSquare square18;
private BasicSquare square19;
private BasicSquare square20;
private SpecialSquare square21;
private BasicSquare square22;
private SpecialSquare square23;
private BasicSquare square24;
private BasicSquare square25;
private SpecialSquare square26;
private BasicSquare square27;
private BasicSquare square28;
private SpecialSquare square29;
private BasicSquare square30;
private SpecialSquare square31;
private BasicSquare square32;
private BasicSquare square33;
private Square square34;
private BasicSquare square35;
private Square square36;
private Square square37;
private BasicSquare square38;
private Square square39;
private BasicSquare square40;

private Player player1;
private Player player2;
public ArrayList<Square> boardArrayList;
private String Tag = "Manager.java";


/*playerArray[0].setPostion(boardArray[0]);
playerArray[1].setPostion(boardArray[0]);*/

	public manager(Context c){

		this.c = (MainActivity)c;
		player1 = new Player();
		player2 = new Player();
		playerArray = new ArrayList<Player>();
		GoSquare square1 = new GoSquare("Go");

		playerArray.add(player1);
		playerArray.add(player2);
		//TODO set back to 40
		boardArray = new Square[28];

		currentPlayer = playerArray.get(0);
		
		square2 = new BasicSquare(1, 60, "Mediterranean Avenua");
		square3 = new SpecialSquare("Special", c);
		square4 = new BasicSquare(1, 60, "Baltic Avenue");
		square5 = new SpecialSquare("Special", c);
		square6 = new SpecialSquare("Special", c);
		square7 = new BasicSquare(2, 100, "Oriental Avenue");
		square8 = new JailSquare("Jail");
		square9 = new BasicSquare(2, 100, "Vermont Avenue");
		square10 = new BasicSquare(2, 120, "Connecticut Avenue");
		square11 = new SpecialSquare("Special", c);
		square12 = new BasicSquare(3, 140, "St. Charles Place");
		square13 = new SpecialSquare("Special", c);
		square14 = new BasicSquare(3, 140, "States Avenue");
		square15 = new BasicSquare(3, 160, "Virginia Avenue");
		square16 = new SpecialSquare("Special", c);
		square17 = new BasicSquare(4, 180, "St. Jame place");
		square18 = new SpecialSquare("Special", c);
		square19 = new BasicSquare(4, 180, "Tennesee Avenue");
		square20 = new BasicSquare(4, 200, "New York Avenue");
		square21 = new SpecialSquare("Special", c);
		square22 = new BasicSquare(5, 220, "Kentucky Avenue");
		square23 = new SpecialSquare("Special", c);
		square24 = new BasicSquare(5, 220, "Indiana Avenue");
		square25 = new BasicSquare(5, 350, "Park Place");
		square26 = new SpecialSquare("Special", c);
		square27 = new BasicSquare(6, 400, "Boardwalk");
		square28 = new BasicSquare(6, 300, "North Carolina Avenue");
		/*square29 = new Square();
		square30 = new BasicSquare(6, 280, "Marvin Gardens");
		square31 = new Square();
		square32 = new BasicSquare(7, 300, "Pacific Avenue");
		square33 = new BasicSquare(7, 300, "North Carolina Avenue");
		square34 = new Square();
		square35 = new BasicSquare(7, 320, "Pennyslvania Avenue");
		square36 = new Square();
		square37 = new Square();
		square38 = new BasicSquare(8, 350, "Park Place");
		square39 = new Square();
		square40 = new BasicSquare(8, 400, "Boardwalk");*/
		boardArray[0] = square1;
		boardArray[1] = square2;
		boardArray[2] = square3;
		boardArray[3] = square4;
		boardArray[4] = square5;
		boardArray[5] = square6;
		boardArray[6] = square7;
		boardArray[7] = square8;
		boardArray[8] = square9;
		boardArray[9] = square10;
		boardArray[10] = square11;
		boardArray[11] = square12;
		boardArray[12] = square13;
		boardArray[13] = square14;
		boardArray[14] = square15;
		boardArray[15] = square16;
		boardArray[16] = square17;
		boardArray[17] = square18;
		boardArray[18] = square19;
		boardArray[19] = square20;
		boardArray[20] = square21;
		boardArray[21] = square22;
		boardArray[22] = square23;
		boardArray[23] = square24;
		boardArray[24] = square25;
		boardArray[25] = square26;
		boardArray[26] = square27;
		boardArray[27] = square28;
		/*boardArray[28] = square29;
		boardArray[29] = square30;
		boardArray[30] = square31;
		boardArray[31] = square32;
		boardArray[32] = square33;
		boardArray[33] = square34;
		boardArray[34] = square35;
		boardArray[35] = square36;
		boardArray[36] = square37;
		boardArray[37] = square38;
		boardArray[38] = square39;
		boardArray[39] = square40;*/
		boardArrayList = new ArrayList<Square>();
		for(Square x: boardArray){
			if(x != null)
				boardArrayList.add(x);
		}
		
		playerArray.get(0).setPosition(boardArray[0]);
		playerArray.get(1).setPosition(boardArray[0]);
		
		playerArray.get(0).setmPaintColor(Color.RED);
		playerArray.get(1).setmPaintColor(Color.BLUE);
	}
	//To be called when player hits throw dice button
	public void movePlayer()
	{
	/*	if(currentPlayer.getHasThrownDice() == false){
		int amount = currentPlayer.throwDice();
		for(int i = 0; i < amount; i++)
		{
			if(currentPlayer.getPositionInBoard() > 40)
			{
				currentPlayer.SetPositionInBoard(0);
			}
			currentPlayer.increasePositionInBoard();
		}*/
		currentPlayer.setPosition(boardArray[currentPlayer.getPositionInBoard()]);
		//currentPlayer.setHasThrownDice(true);
		currentPlayer.getPosition().duAction(currentPlayer);
		
	}
	
	public void Buy(Square s)
	{
		
		if(s.typeId == 1){
			Log.i(Tag , "buy house/hotel called");
			currentPlayer.buyProperty((BasicSquare)s);
			currentPlayer.buyHouse((BasicSquare)s);
			currentPlayer.buyHotel((BasicSquare)s);
		}
	}
	
	//To be called when player clicks End turn button

	public void changePlayer()
	{

		currentPlayer.setHasThrownDice(false);
		if(currentPlayer.getId() == 1)
		{
			currentPlayer = playerArray.get(1);
		}
		else
		{
			currentPlayer = playerArray.get(0);
		}
		
		if(playerArray.get(0).getIsInJail() == true)
		{
			currentPlayer = playerArray.get(1);
			playerArray.get(0).setIsInJail(false);
		}
		else if(playerArray.get(1).getIsInJail() == true)
		{
			currentPlayer = playerArray.get(0);
			playerArray.get(1).setIsInJail(false);
		}
		
	}



}