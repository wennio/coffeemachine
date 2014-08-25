package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private ComponentsFactory fac;
	private int total;
	private ArrayList<Coin> listaMoedasInseridas;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		// TODO Auto-generated constructor stub
		fac = factory;
		total = 0;
		listaMoedasInseridas = new ArrayList<Coin>();
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
		fac.getDisplay().warn(Messages.CANCEL);
		devolverMoedas();
		fac.getDisplay().info(Messages.INSERT_COINS);
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

	public void select(Drink drink) {
		// TODO Auto-generated method stub
		switch (drink) {
		case BLACK:
			//verifyBlackPlan
			fac.getCupDispenser().contains(1);	//inOrder.verify(cupDispenser).contains(1);
			fac.getWaterDispenser().contains(1.1);	//inOrder.verify(waterDispenser).contains(anyDouble());
			fac.getCoffeePowderDispenser().contains(1.2);	//inOrder.verify(coffeePowderDispenser).contains(anyDouble());
			
			//verifyBlackMix
			fac.getDisplay().info(Messages.MIXING);	//inOrder.verify(display).info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.3);	//inOrder.verify(coffeePowderDispenser).release(anyDouble());
			fac.getWaterDispenser().release(1.4); 	//inOrder.verify(waterDispenser).release(anyDouble());
			
			//verifyDrinkRelease
			fac.getDisplay().info(Messages.RELEASING); 	//inOrder.verify(display).info(Messages.RELEASING);
			fac.getCupDispenser().release(1); 	//inOrder.verify(cupDispenser).release(1);
			fac.getDrinkDispenser().release(1.6); 	//inOrder.verify(drinkDispenser).release(anyDouble());
			fac.getDisplay().info(Messages.TAKE_DRINK); 	//inOrder.verify(display).info(Messages.TAKE_DRINK);
			
			fac.getDisplay().info(Messages.INSERT_COINS); 	//verify(display).info(Messages.INSERT_COINS);

			break;
		case BLACK_SUGAR:
			//verifyBlackPlan
			fac.getCupDispenser().contains(1);	//inOrder.verify(cupDispenser).contains(1);
			fac.getWaterDispenser().contains(1.1);	//inOrder.verify(waterDispenser).contains(anyDouble());
			fac.getCoffeePowderDispenser().contains(1.2);	//inOrder.verify(coffeePowderDispenser).contains(anyDouble());
			
			fac.getSugarDispenser().contains(2.1);
			
			//verifyBlackMix
			fac.getDisplay().info(Messages.MIXING);	//inOrder.verify(display).info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.3);	//inOrder.verify(coffeePowderDispenser).release(anyDouble());
			fac.getWaterDispenser().release(1.4); 	//inOrder.verify(waterDispenser).release(anyDouble());
			
			fac.getSugarDispenser().release(2.2);
			
			//verifyDrinkRelease
			fac.getDisplay().info(Messages.RELEASING); 	//inOrder.verify(display).info(Messages.RELEASING);
			fac.getCupDispenser().release(1); 	//inOrder.verify(cupDispenser).release(1);
			fac.getDrinkDispenser().release(1.6); 	//inOrder.verify(drinkDispenser).release(anyDouble());
			fac.getDisplay().info(Messages.TAKE_DRINK); 	//inOrder.verify(display).info(Messages.TAKE_DRINK);
			
			fac.getDisplay().info(Messages.INSERT_COINS); 	//verify(display).info(Messages.INSERT_COINS);
		default:
			break;
		}
		
	}

}
