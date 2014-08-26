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
		//verifyBlackPlan
		factory.getCupDispenser().contains(1);
		factory.getWaterDispenser().contains(1.1);
		factory.getCoffeePowderDispenser().contains (1.2);
		
		if(drink == Drink.BLACK_SUGAR){
			factory.getSugarDispenser().contains(2.1);
		}
		
		//verifyBlackSugarMix
		factory.getDisplay().info("Mixing ingredients.");	
		factory.getCoffeePowderDispenser().release (1.3); 
		factory.getWaterDispenser().release (1.4);
		
		if(drink == Drink.BLACK_SUGAR){
			factory.getSugarDispenser().release(2.2);
		}
		
		//verifyDrinkRelease
		factory.getDisplay().info("Releasing drink.");
		factory.getCupDispenser().release (1);
		factory.getDrinkDispenser().release (1.5);
		display.info("Please, take your drink.");		
		display.info("Insert coins and select a drink!");
		
		moedas.clear();
	}
	
}