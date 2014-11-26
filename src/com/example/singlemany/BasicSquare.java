package com.example.singlemany;

import android.graphics.Color;

public class BasicSquare extends Square{

	private int monopoly, houses, purchasePrice, Id;
	double housePrice, hotelPrice;
	double baseRent; 
	private Player owner;
	private boolean hasHotel, hasMaxHouses, hasOwner;
	

	
	
	public BasicSquare(int monopoly, int purchasePrice, String name)
	{
		this.monopoly = monopoly;
		this.purchasePrice = purchasePrice;
		hotelPrice = purchasePrice * 5;
		housePrice = purchasePrice * 2.5; 
		baseRent = purchasePrice * 0.1;
		hasHotel = false;
		hasMaxHouses = false;
		hasOwner = false;
		houses = 0;
		super.setName(name);
		super.typeId = 1;
		
		//set paint defaults
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
		
	}
	
	public void buildHouse()
	{
		if(houses < 4){
			houses++; 
		}
		else
			hasMaxHouses = true;
	}
	
	public void buildHotel()
	{
		if(hasHotel == false)
		{
			hasHotel = true;
		}
	}
	
	public boolean getHasMaxHouses()
	{
		return hasMaxHouses;
	}
	
	public boolean getHasHotel()
	{
		return hasHotel;
	}
	
	public double getBaseRent()
	{
		if(hasHotel == false)
		{
			switch(houses)
			{
			case 0:
				return baseRent;
			case 1:
				return baseRent * 1.8;
			case 2:
				return baseRent * 2.5;
			case 3:
				return baseRent * 3.6;
			case 4:
				return baseRent *  5;
			}
		}else{
				return baseRent * 8.25;
		}
		//This statement shouldn't ever execute, but if it does you'll know something's wrong
		return 10000000000.00;
	}
	
	public int getHouses()
	{
		return houses;
	}
	
	public double getPurchasePrice()
	{
		return purchasePrice;
	}
	
	public void setOwner(Player owner)
	{
		this.owner = owner;
		hasOwner = true;
	}
	
	public Player getOwner()
	{
		return owner;
	}
	
	public boolean getHasOwner()
	{
		return hasOwner;
	}
	public double getHousePrice(){
		return housePrice;
	}
	public double getHotelPrice(){
		return hotelPrice;
	}
	
	
	
	@Override
 	public void duAction(Player player) {
		if(this.getHasOwner() == true && this.getOwner() != player)
		{
			double rent = this.getBaseRent();
			owner.addMoney(rent);
			player.payMoney(rent);
		}
		
	}

}
