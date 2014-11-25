package com.example.singlemany;

public class manager {

Square[] boardArray;
Player[] playerArray;	

Player player1 = new Player();
Player player2 = new Player();

playerArray[0] = player1;
playerArray[1] = player2;

Player currentPlayer = playerArray[0];

GoSquare square1 = new GoSquare();
BasicSquare square2 = new BasicSquare(1, 60, "Mediterranean Avenua");
Square square3 = new Square();
BasicSquare square4 = new BasicSquare(1, 60, "Baltic Avenue");
Square square5 = new Square();
Square square6 = new Square();
BasicSquare square7 = new BasicSquare(2, 100, "Oriental Avenue");
Square square8 = new Square();
BasicSquare square9 = new BasicSquare(2, 100, "Vermont Avenue");
BasicSquare square10 = new BasicSquare(2, 120, "Connecticut Avenue");
Square square11 = new Square();
BasicSquare square12 = new BasicSquare(3, 140, "St. Charles Place");
Square square13 = new Square();
BasicSquare square14 = new BasicSquare(3, 140, "States Avenue");
BasicSquare square15 = new BasicSquare(3, 160, "Virginia Avenue");
Square square16 = new Square();
BasicSquare square17 = new BasicSquare(4, 180, "St. Jame place");
Square square18 = new Square();
BasicSquare square19 = new BasicSquare(4, 180, "Tennesee Avenue");
BasicSquare square20 = new BasicSquare(4, 200, "New York Avenue");
Square square21 = new Square();
BasicSquare square22 = new BasicSquare(5, 220, "Kentucky Avenue");
Square square23 = new Square();
BasicSquare square24 = new BasicSquare(5, 220, "Indiana Avenue");
BasicSquare square25 = new BasicSquare(5, 240, "Illinois Avenue");
Square square26 = new Square();
BasicSquare square27 = new BasicSquare(6, 260, "Atlantic Avenue");
BasicSquare square28 = new BasicSquare(6, 260, "Ventinor Avenue");
Square square29 = new Square();
BasicSquare square30 = new BasicSquare(6, 280, "Marvin Gardens");
Square square31 = new Square();
BasicSquare square32 = new BasicSquare(7, 300, "Pacific Avenue");
BasicSquare square33 = new BasicSquare(7, 300, "North Carolina Avenue");
Square square34 = new Square();
BasicSquare square35 = new BasicSquare(7, 320, "Pennyslvania Avenue");
Square square36 = new Square();
Square square37 = new Square();
BasicSquare square38 = new BasicSquare(8, 350, "Park Place");
Square square39 = new Square();
BasicSquare square40 = new BasicSquare(8, 400, "Boardwalk");


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

playerArray[0].setPostion(boardArray[0]);
playerArray[1].setPostion(boardArray[0]);

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

//To be called when player clicks End turn button
public void changePlayer()
{
	
}



}