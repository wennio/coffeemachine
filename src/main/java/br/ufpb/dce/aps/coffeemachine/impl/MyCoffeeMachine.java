package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine{

	//Variáveis
	private ComponentsFactory factory;
	private int dolar, cents;
	
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory; 
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		dolar = dime.getValue() / 100;
		cents = dime.getValue() % 100;
		factory.getDisplay().info("Total: US$ " + dolar + "." + cents + "");
	}

}
