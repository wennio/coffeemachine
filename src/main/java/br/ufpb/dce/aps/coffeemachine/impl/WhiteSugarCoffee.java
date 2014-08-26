package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class WhiteSugarCoffee extends WhiteCoffee{
	public WhiteSugarCoffee(ComponentsFactory factory) {
		super(factory);
	}

	public boolean whiteSugarPlan(){
		if(!whitePlan()){
			return false;
		}
		getFactory().getSugarDispenser().contains(5.0);
		return true;
	}

	public void whiteSugarMix(){
		whiteMix();
		getFactory().getSugarDispenser().release(5.0);
	}
}
