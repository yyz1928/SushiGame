package sushigame.model;

import rawMaterials.PlatePriceException;
import rawMaterials.RedPlate;
import rawMaterials.Sashimi;

public class SushiGameModel {

	private BeltImpl belt;
	private Customer[] customers;
	private Chef[] opponent_chefs;
	private Chef player_chef;

	private final double STARTING_BALANCE = 100.0;

	public SushiGameModel(int belt_size, int num_customers, int num_chef_opponents) {
		if (belt_size < 1) {
			throw new IllegalArgumentException("Belt must have size > 0");
		}

		if (belt_size < num_customers) {
			throw new IllegalArgumentException("Belt size must be greater then number of customers");
		}

		belt = new BeltImpl(belt_size);
		customers = new Customer[num_customers];
		opponent_chefs = new Chef[num_chef_opponents];

		int belt_idx = 0;
		for (int i=0; i<num_customers; i++) {
			customers[i] = new RandomCustomer(Math.random()); //Gives random pickiness to each customer
			belt.setCustomerAtPosition(customers[i], belt_idx); 
			belt_idx += belt_size / num_customers; //both numbers are ints, so the result is an int
		}

		for (int i=0; i<num_chef_opponents; i++) {
			opponent_chefs[i] = new ChefImpl("Opponent Chef " + i, STARTING_BALANCE, belt); //Sets each Chef on the belt and gives them $100
		}
		player_chef = new ChefImpl("Player", STARTING_BALANCE, belt);
	}

	public Chef getPlayerChef() {
		return player_chef;
	}

	public Chef[] getOpponentChefs() {
		return opponent_chefs.clone();
	}

	public Belt getBelt() {
		return belt;
	}
}
