package com.example.singlemany;

public class manager {

Square[] boardArray;
Player[] playerArray;

/*playerArray[0] = player1;
playerArray[1] = player2;*/

Player currentPlayer;

//GoSquare square1;
Square square1; //TODO change to gosquare
BasicSquare square2;
Square square3;
BasicSquare square4;

private Square square5;

private Square square6;

private BasicSquare square7;

private Square square8;

private BasicSquare square9;

private BasicSquare square10;

private Square square11;

private BasicSquare square12;

private Square square13;

private BasicSquare square14;

private BasicSquare square15;

private Square square16;

private BasicSquare square17;

private Square square18;

private BasicSquare square19;

private BasicSquare square20;

private Square square21;

private BasicSquare square22;

private Square square23;

private BasicSquare square24;

private BasicSquare square25;

private Square square26;

private BasicSquare square27;

private BasicSquare square28;

private Square square29;

private BasicSquare square30;

private Square square31;

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


/*playerArray[0].setPostion(boardArray[0]);
playerArray[1].setPostion(boardArray[0]);*/

	public manager(){
		currentPlayer = playerArray[0];
		player1 = new Player();
		player2 = new Player();
		GoSquare square1 = new GoSquare();

		playerArray[0] = player1;
		playerArray[1] = player2;
		boardArray = new Square[40];
		playerArray = new Player[2];
		
		square2 = new BasicSquare(1, 60, "Mediterranean Avenua");
		square3 = new Square();
		square4 = new BasicSquare(1, 60, "Baltic Avenue");
		square5 = new Square();
		square6 = new Square();
		square7 = new BasicSquare(2, 100, "Oriental Avenue");
		square8 = new Square();
		square9 = new BasicSquare(2, 100, "Vermont Avenue");
		square10 = new BasicSquare(2, 120, "Connecticut Avenue");
		square11 = new Square();
		square12 = new BasicSquare(3, 140, "St. Charles Place");
		square13 = new Square();
		square14 = new BasicSquare(3, 140, "States Avenue");
		square15 = new BasicSquare(3, 160, "Virginia Avenue");
		square16 = new Square();
		square17 = new BasicSquare(4, 180, "St. Jame place");
		square18 = new Square();
		square19 = new BasicSquare(4, 180, "Tennesee Avenue");
		square20 = new BasicSquare(4, 200, "New York Avenue");
		square21 = new Square();
		square22 = new BasicSquare(5, 220, "Kentucky Avenue");
		square23 = new Square();
		square24 = new BasicSquare(5, 220, "Indiana Avenue");
		square25 = new BasicSquare(5, 240, "Illinois Avenue");
		square26 = new Square();
		square27 = new BasicSquare(6, 260, "Atlantic Avenue");
		square28 = new BasicSquare(6, 260, "Ventinor Avenue");
		square29 = new Square();
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
		square40 = new BasicSquare(8, 400, "Boardwalk");
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
		boardArray[28] = square29;
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
		boardArray[39] = square40;
		
		playerArray[0].setPosition(boardArray[0]);
		playerArray[1].setPosition(boardArray[0]);
	}
	//To be called when player hits throw dice button
	public void movePlayer()
	{
		if(currentPlayer.getHasThrownDice() == false){
		int amount = currentPlayer.throwDice();
		for(int i = 0; i < amount; i++)
		{
			if(currentPlayer.getPositionInBoard() > 40)
			{
				currentPlayer.SetPositionInBoard(0);
			}
			currentPlayer.increasePositionInBoard();
		}
		currentPlayer.setPosition(boardArray[currentPlayer.getPositionInBoard()]);
		currentPlayer.setHasThrownDice(true);
		currentPlayer.getPosition().duAction(currentPlayer);
		
		}
		
	}
	
	public void Buy()
	{
		if(currentPlayer.getPosition().typeId == 1){
			currentPlayer.buyProperty((BasicSquare)currentPlayer.getPosition());
			currentPlayer.buyHouse((BasicSquare)currentPlayer.getPosition());
			currentPlayer.buyHotel((BasicSquare)currentPlayer.getPosition());
		}
	}
	
	//To be called when player clicks End turn button
	public void changePlayer()
	{
		
	}



}