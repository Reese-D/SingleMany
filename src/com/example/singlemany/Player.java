package com.example.singlemany;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
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
	private boolean isInJail;
	private Bitmap image;
	private ImageView imageView;
	private float xActual, yActual;
	private Paint mPaint;
	Color playerColor;
	private String Tag = "Player.java";
	
	public Player(){
		image = null;
		amountOfPlayers++;
		Id = amountOfPlayers;
		money = 2000;
		hasThrownDice = false;
		positionInBoard = 0;
		movingToPosition = 0;
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2f);
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
		//defualt color red, should be set manually however
		mPaint.setColor(Color.RED);
		isInJail = false;
		
		//position = 0; //TODO position is square type, change this
	}
	public boolean getIsInJail()
	{
		return isInJail;
	}
	public void setIsInJail(boolean option)
	{
		isInJail = option;
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
		int diceValue1 = (int )(Math.random() * 6 + 1);
		return diceValue1;
		
	}
	public void buyProperty(BasicSquare property)
	{
		Log.i(Tag , "attempted to purchase property");
		if(property.getHasOwner() == false && this.money >= property.getPurchasePrice())
		{
			Log.i(Tag , "Purchased property, money was: " + money);
			money = money - property.getPurchasePrice();
			Log.i(Tag , "money is now: " + money);
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
		Log.i(Tag, "current player has thrown dice: "+ set);
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

	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaintColor(int color) {
		mPaint.setColor(color);
	}
	
}
