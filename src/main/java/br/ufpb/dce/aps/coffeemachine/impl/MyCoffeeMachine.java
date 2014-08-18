package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {
	
	ComponentsFactory fac;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		fac.getDisplay().info("Total: US$ 0.10");
	}

}
