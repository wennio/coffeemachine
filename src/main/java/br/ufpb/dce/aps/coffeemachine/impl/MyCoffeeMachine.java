package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Messages;

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
			factory.getDisplay().warn(Messages.CANCEL_MESSAGE);
			for(Coin coin : Coin.reverse()){
				for(Coin aux : moedas){
					if(aux == coin){
						cashBox.release(aux);
					}
				}
			}
			factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
		}
	}
	
}