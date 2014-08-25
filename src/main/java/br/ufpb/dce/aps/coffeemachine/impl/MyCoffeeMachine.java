package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private ComponentsFactory fac;
	private int total;
	private ArrayList<Coin> listaMoedasInseridas = new ArrayList<Coin>();
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		// TODO Auto-generated constructor stub
		fac = factory;
		total = 0;
		fac.getDisplay().info("Insert coins and select a drink!");
		
	}
	
	//Meus metodos
	
	public void devolverMoedas(){
		for (Coin coin : Coin.reverse()) {
			for (Coin moeda : listaMoedasInseridas) {
				if(moeda == coin){
					fac.getCashBox().release(moeda);
				}
			}
		}
	}
	
	public void tarefaDevolverMoeda(){
		fac.getDisplay().warn(Messages.CANCEL_MESSAGE);
		devolverMoedas();
		fac.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
	}
	
	//Metodos do teste

	public void insertCoin(Coin moeda) {
		// TODO Auto-generated method stub
		if (moeda == null){
			throw new CoffeeMachineException("Insert null coin");
		}
		total += moeda.getValue();
		fac.getDisplay().info("Total: US$ " + total/100 + "." + total%100);
		listaMoedasInseridas.add(moeda);
		
	}

	public void cancel() {
		// TODO Auto-generated method stub
		if(this.total == 0){
			throw new CoffeeMachineException("Cancel without inserting coins");
		}else if(listaMoedasInseridas.size() > 0){
			tarefaDevolverMoeda();
		}
	}

}
