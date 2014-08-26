package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{

	//Variáveis
	private ComponentsFactory factory;
	private int dolar, cents;
	private Display display;
	private CashBox cashBox;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		dolar = 0;
		cents = 0;
		this.factory = factory;
		this.display = factory.getDisplay();
		this.cashBox = factory.getCashBox();
		display.info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		if (dime != null){
			dolar += dime.getValue() / 100;
			cents += dime.getValue() % 100;
			factory.getDisplay().info("Total: US$ " + dolar + "." + cents + "");
		}else {
			throw new CoffeeMachineException("Insert null coin");
		}
	}

	public void cancel() {
		if((dolar == 0) && (cents == 0 )){
			throw new CoffeeMachineException("Cancel without inserting coins");
		}else {
			display.warn(Messages.CANCEL_MESSAGE);
			cashBox.release(Coin.halfDollar);
			display.info(Messages.INSERT_COINS_MESSAGE);
		}
	}

}
