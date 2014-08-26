package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {
	
	//Variáveis
	private ComponentsFactory factory;
	private int cents, dolar; 
	private Display display;
	private CashBox cashBox;
	private ArrayList<Coin> moedas;
	private final int COFFEBLACK = 35;
	private int troco;
	private boolean retornarTroco = false;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		cents = 0;
		dolar = 0;
		this.factory = factory;
		this.display= factory.getDisplay();
		this.cashBox = factory.getCashBox();
		this.moedas = new ArrayList<Coin>();
		this.display.info("Insert coins and select a drink!");
	}
	
	//Meus métodos
	
	public void retornarMoedas(){
		for(Coin coin : Coin.reverse()){
			for(Coin aux : moedas){
				if(aux == coin){
					cashBox.release(aux);
				}
			}
		}
		novaSessao();
	}
	
	public void limparMoedas(){
		moedas.clear();
	}
	
	public void novaSessao(){
		limparMoedas();
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}
	
	public void planejamento(int troco){
		for (Coin coin : Coin.reverse()) {
			if (coin.getValue() <= troco) {
				cashBox.count(coin);
				troco -= coin.getValue();
			}
		}
	}
	
	public void releaseCoins(int troco){
		for (Coin coin : Coin.reverse()) {
			if (coin.getValue() <= troco) {
				cashBox.release(coin);
				troco -= coin.getValue();
			}
		}
	}
	
	public int calculaTroco(){
		int somatorioMoedas = 0;
		for (Coin aux : moedas) {
			somatorioMoedas += aux.getValue();
		}
		return  somatorioMoedas - COFFEBLACK;
	}
	
	//Métodos do teste
	
	public void insertCoin(Coin coin) {
		if (coin != null) {
			moedas.add(coin);
			cents += coin.getValue() % 100;
			dolar += coin.getValue() / 100;
			factory.getDisplay().info("Total: US$ " + dolar + "." + cents + "");
			this.troco = (dolar + cents) - COFFEBLACK;
			if(troco > 0){
				retornarTroco = true;
			}
		} else {
			throw new CoffeeMachineException("");
		}

	}

	public void cancel() {
		if ((cents == 0) && (dolar == 0)){
			throw new CoffeeMachineException("");
		}
		factory.getDisplay().warn("Cancelling drink. Please, get your coins.");
		if(moedas.size() > 0){
			retornarMoedas();
		}
	}

	public void select(Drink drink) {
		//verifyBlackPlan
		if(!factory.getCupDispenser().contains(1)){
			display.warn(Messages.OUT_OF_CUP);
			retornarMoedas();
			return;
		}
		if(!factory.getWaterDispenser().contains(1.1)){
			display.warn(Messages.OUT_OF_WATER);
			retornarMoedas();
			return;
		}
		if(!factory.getCoffeePowderDispenser().contains (1.2)){
			display.warn(Messages.OUT_OF_COFFEE_POWDER);
			retornarMoedas();
			return;
		}
		
		if (drink == Drink.WHITE) {
			factory.getCreamerDispenser().contains(2.0);
		}
		
		if (drink == Drink.WHITE_SUGAR) {
			factory.getCreamerDispenser().contains(2.4);
			factory.getSugarDispenser().contains(2.5);
		}

		if(drink == Drink.BLACK_SUGAR){
			if(!factory.getSugarDispenser().contains(2.1)){
				display.warn(Messages.OUT_OF_SUGAR);
				retornarMoedas();
				return;
			}
		}
		
		planejamento(calculaTroco());
		
		//verifyBlackSugarMix
		factory.getDisplay().info("Mixing ingredients.");	
		factory.getCoffeePowderDispenser().release (1.3); 
		factory.getWaterDispenser().release (1.4);
		
		if (drink == Drink.WHITE) {
			factory.getCreamerDispenser().release(2.0);
		
		}
		
		if (drink == Drink.WHITE_SUGAR) {
			factory.getCreamerDispenser().release(2.4);
			factory.getSugarDispenser().release(2.5);
		}
		
		if(drink == Drink.BLACK_SUGAR){
			factory.getSugarDispenser().release(2.2);
		}
		
		//verifyDrinkRelease
		factory.getDisplay().info("Releasing drink.");
		factory.getCupDispenser().release (1);
		factory.getDrinkDispenser().release (1.5);
		display.info("Please, take your drink.");		
		
		releaseCoins(calculaTroco());
		
		novaSessao();
	}
	
}