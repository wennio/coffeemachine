package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private ComponentsFactory fac;
	private int total;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		// TODO Auto-generated constructor stub
		fac = factory;
		total = 0;
		fac.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin dime) {
		// TODO Auto-generated method stub
		if (dime == null){
			throw new CoffeeMachineException("Insert null coin");
		}
		total += dime.getValue();
		fac.getDisplay().info("Total: US$ " + total/100 + "." + total%100);
		
	}

	public void cancel() {
		// TODO Auto-generated method stub
		if(this.total == 0){
			throw new CoffeeMachineException("Cancel without inserting coins");
		}
		fac.getDisplay().warn(Messages.CANCEL_MESSAGE);
		fac.getCashBox().release(Coin.halfDollar);
		fac.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
	}

}
