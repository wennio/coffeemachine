package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {
	
	private ComponentsFactory fac;
	private int total;
	private ArrayList<Coin> coins;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
		coins = new ArrayList<Coin>();
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
		fac.getDisplay().warn("Cancelling drink. Please, get your coins.");
		this.retornarMoedas();
		fac.getDisplay().info("Insert coins and select a drink!");
	}
	
	public void blackPlan(){
		fac.getCupDispenser().contains(1);
		fac.getWaterDispenser().contains(1.1);
		fac.getCoffeePowderDispenser().contains(1.2);
	}
	
	public void blackMix(){
		fac.getDisplay().info(Messages.MIXING);
		fac.getCoffeePowderDispenser().release(1.3);
		fac.getWaterDispenser().release(1.4);
	}
	
	public void drinkRelease(){
		fac.getDisplay().info(Messages.RELEASING);
		fac.getCupDispenser().release(1);
		fac.getDrinkDispenser().release(1.5);
		fac.getDisplay().info(Messages.TAKE_DRINK);
		fac.getDisplay().info(Messages.INSERT_COINS);
	}
	//Métodos do teste
	
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
		}else if(coins.size() >= 1){
			this.tarefaCompletaDevolverMoedas();
		}
	}
	
	public void select(Drink drink) {
		switch (drink) {
		case BLACK:
			fac.getCupDispenser().contains(1);
			if(!(fac.getWaterDispenser().contains(3.0))){
				fac.getDisplay().warn(Messages.OUT_OF_WATER);
				retornarMoedas();
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			if(!(fac.getCoffeePowderDispenser().contains(3.0))){
				fac.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				retornarMoedas();
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			this.blackMix();	
			this.drinkRelease();
			break;
		case BLACK_SUGAR:
			this.blackPlan();
			if(!(fac.getSugarDispenser().contains(2.1))){
				fac.getDisplay().warn(Messages.OUT_OF_SUGAR);
				retornarMoedas();
				fac.getDisplay().info(Messages.INSERT_COINS);
				return;
			}
			this.blackMix();
			fac.getSugarDispenser().release(2.2);
			this.drinkRelease();
			this.coins.clear();
			break;
		default:
			break;
		}
	}
	

	
}
