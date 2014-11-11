package com.example.singlemany;

public class Player {
	private int Id;
	private Square position;
	private static int amountOfPlayers = 0;
	private BasicSquare[] properties;
	private double money;
	
	public Player(){
		amountOfPlayers++;
		Id = amountOfPlayers;
		money = 2000;
		position = 0; //TODO position is square type, change this
	}
	
	public double getMoney()
	{
		return money;
	}
	public void addMoney(double amount)
	{
		money = money + amount;
	}
	public void setPosition(int diceSum)
	{
		this.position = position + diceSum; //TODO can't add square object to integer
	}
	public void buyProperty(BasicSquare property)
	{
		if(property.getHasOwner() == false)
		{
			money = money - property.getPurchasePrice();
			property.setOwner(this);
		}
	}
	public void buyHouse(BasicSquare property)
	{
		if(property.getOwner() == this && property.getHouses() < 4)
		{
			money = money - property.getHousePrice();
			property.buildHouse();
		}
	}
	public void buyHotel(BasicSquare property)
	{
		if(property.getHasHotel() == false && property.getHouses() == 4)
		{
			money = money - property.getHotelPrice();
			property.buildHotel();
		}
	}


}
