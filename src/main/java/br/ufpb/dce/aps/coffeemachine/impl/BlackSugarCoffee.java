package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class BlackSugarCoffee extends BlackCoffee{
	
	public BlackSugarCoffee(ComponentsFactory factory){
		super(factory);
	}
	
	public boolean blackSugarPlan(){
		if (!blackPlan()) {
			return false;
		}
		if (!getFactory().getSugarDispenser().contains(5.0)) {
			WarnMessage.setWarnMessage(Messages.OUT_OF_SUGAR); 
			return false;
		}
		return true;
	}
	
	
	public void blackSugarMix(){
		blackMix();
		getFactory().getSugarDispenser().release(5.0);
	}
}
