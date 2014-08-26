package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private int cents, dolar; 
	private Display display;
	private CashBox cashBox;
	private ArrayList<Coin> moedas;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		cents = 0;
		dolar = 0;
		this.factory = factory;
		this.display= factory.getDisplay();
		this.cashBox = factory.getCashBox();
		this.moedas = new ArrayList<Coin>();
		this.display.info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) {
		if (coin != null) {
			moedas.add(coin);
			cents += coin.getValue() % 100;
			dolar += coin.getValue() / 100;
			factory.getDisplay().info("Total: US$ " + dolar + "." + cents + "");
		} else {
			throw new CoffeeMachineException("");
		}

	}

	public void cancel() {
		if ((cents == 0) && (dolar == 0)){
			throw new CoffeeMachineException("");
		}
		
		if(moedas.size() > 0){
			factory.getDisplay().warn("Cancelling drink. Please, get your coins.");
			for(Coin coin : Coin.reverse()){
				for(Coin aux : moedas){
					if(aux == coin){
						cashBox.release(aux);
					}
				}
			}
			factory.getDisplay().info("Insert coins and select a drink!");
		}
	}

	public void select(Drink drink) {
		factory.getCupDispenser().contains(1);
		factory.getWaterDispenser().contains(0.5);
		factory.getCoffeePowderDispenser().contains (0.8); 
		factory.getDisplay().info("Mixing ingredients.");	
		factory.getCoffeePowderDispenser().release (0.6); 
		factory.getWaterDispenser().release (0.9);
		factory.getDisplay().info("Releasing drink.");
		factory.getCupDispenser().release (1);
		factory.getDrinkDispenser().release (0.3);
		factory.getDisplay().info("Please, take your drink.");		
		factory.getDisplay().info("Insert coins and select a drink!");
	}
	
}