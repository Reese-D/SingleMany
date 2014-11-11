package com.example.singlemany;

public class BasicSquare extends Square{

	private int monopoly, houses, purchasePrice;
	double housePrice, hotelPrice;
	double baseRent; 
	private Player owner;
	private boolean hasHotel, hasMaxHouses, hasOwner;
	
	public BasicSquare(int monopoly, int purchasePrice)
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
				break;
			case 1:
				return baseRent * 1.8;
				break;
			case 2:
				return baseRent * 2.5;
				break;
			case 3:
				return baseRent * 3.6;
				break;
			case 4:
				return baseRent *  5;
				break;
			}
		}
			else
				return baseRent * 8.25;
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
 	public void duAction(Player) {
		// TODO Auto-generated method stub
		
	}

}
