package sushigame.model;

import rawMaterials.Plate;

public class RandomCustomer implements Customer {

	private double pickiness;
	
	//The higher the pickiness the more likely the Customer is going to eat it
	public RandomCustomer(double pickiness) {
		this.pickiness = 0;
	}

	@Override
	public boolean consumesPlate(Plate p) {
		return (Math.random() < pickiness);
	}

}
