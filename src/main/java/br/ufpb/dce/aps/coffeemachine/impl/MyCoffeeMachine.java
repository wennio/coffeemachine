package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {
	
	private ComponentsFactory fac;
	private int total;
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin moeda) {
		if(moeda == null){
			throw new CoffeeMachineException("Insert null coin");
		}
		total += moeda.getValue();
		fac.getDisplay().info("Total: US$ " + total/100 + "." + total%100);
		coins.add(moeda);
	}

	public void cancel() {
		if (this.total == 0) {
			throw new CoffeeMachineException(" Cancel without inserting coins");
		}else if(coins.size() >= 2){
			this.tarefaCompletaDevolverMoedas();
		}
		this.tarefaCompletaDevolverMoedas();
	}
	
	//Meus métodos
	
	private void retornarMoedas(){
		Coin[] reverso = Coin.reverse();
		for (Coin r : reverso) {
			for (Coin aux : this.coins) {
				if (aux == r) {
					this.fac.getCashBox().release(aux);
				}
			}
		}
	}
	
	private void tarefaCompletaDevolverMoedas(){
		fac.getDisplay().warn(Messages.CANCEL_MESSAGE);
		this.retornarMoedas();
		fac.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
	}
}
