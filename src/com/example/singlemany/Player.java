package com.example.singlemany;

import java.util.ArrayList;

public class Player {
	private int Id;
	private Square position;
	private static int amountOfPlayers = 0;
	private ArrayList properties = new ArrayList();
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
		
		return value;
	}
	public void buyProperty(BasicSquare property)
	{
		if(property.getHasOwner() == false)
		{
			money = money - property.getPurchasePrice();
			property.setOwner(this);
			properties.add(property);
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
	public int getId()
	{
		return Id;
	}

}
