package com.example.singlemany;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.widget.ImageView;

public class Player {
	public final int velocity = 100;
	private int Id;
	private int positionInBoard;
	private int movingToPosition;
	private Square position;
	public static int amountOfPlayers = 0;
	private ArrayList<BasicSquare> properties = new ArrayList();
	private double money;
	private boolean hasThrownDice;
	private Bitmap image;
	private ImageView imageView;
	private float xActual, yActual;
	Color playerColor;
	
	public Player(){
		image = null;
		amountOfPlayers++;
		Id = amountOfPlayers;
		money = 2000;
		hasThrownDice = false;
		positionInBoard = 0;
		movingToPosition = 0;
		
		//position = 0; //TODO position is square type, change this
	}
	
	public float getxActual(){
		return xActual;
	}
	public float getyActual(){
		return yActual;
	}
	public void setxyActual(float left, float top){
		xActual = left;
		yActual = top;
	}
	
	public void setMovingTo(int i){
		movingToPosition = i;
	}
	
	public int getMovingTo(){
		return movingToPosition;
	}
	
	public void setImage(Bitmap b){
		image = b;
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public double getMoney()
	{
		return money;
	}
	public void addMoney(double amount)
	{
		money = money + amount;
	}
	
	public void payMoney(double amount)
	{
		money = money - amount;
	}
	
	public int throwDice()
	{
		int value;
		int diceValue1 = (int )(Math.random() * 6 + 1);
		int diceValue2 = (int )(Math.random() * 6 + 1);
		value = diceValue1 + diceValue2;
		if(diceValue1 == diceValue2)
		{
			diceValue1 = (int )(Math.random() * 6 + 1);
			diceValue2 = (int )(Math.random() * 6 + 1);
			value = value + diceValue1 + diceValue2;
		}
		hasThrownDice = true;
		return value;
		
	}
	public void buyProperty(BasicSquare property)
	{
		if(property.getHasOwner() == false && this.money >= property.getPurchasePrice())
		{
			money = money - property.getPurchasePrice();
			property.setOwner(this);
			properties.add(property);
		}
	}
	public void buyHouse(BasicSquare property)
	{
		if(property.getOwner() == this && property.getHouses() < 4 && this.money >= property.getHousePrice())
		{
			money = money - property.getHousePrice();
			property.buildHouse();
		}
	}
	public void buyHotel(BasicSquare property)
	{
		if(property.getHasHotel() == false && property.getHouses() == 4 && property.getOwner() == this && this.money >= property.getHotelPrice())
		{
			money = money - property.getHotelPrice();
			property.buildHotel();
		}
	}
	public int getId()
	{
		return Id;
	}
	
	public Square getPosition()
	{
		return position;
	}
	
	public void setPosition(Square square)
	{
		position = square;
	}
	
	public ArrayList<BasicSquare> getProperties()
	{
		return properties;
	}
	
	public boolean getHasThrownDice()
	{
		return hasThrownDice;
	}
	public void setHasThrownDice(boolean set)
	{
		hasThrownDice = set;
	}
	public int getPositionInBoard(){
		return positionInBoard;
	}
	public void increasePositionInBoard()
	{
		positionInBoard = positionInBoard +1;
	}
	public void SetPositionInBoard(int amount){
		positionInBoard = amount;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
}
