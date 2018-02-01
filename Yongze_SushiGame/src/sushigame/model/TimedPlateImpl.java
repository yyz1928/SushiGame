package sushigame.model;


import rawMaterials.Plate;
import rawMaterials.PlatePriceException;
import rawMaterials.Sushi;

public class TimedPlateImpl implements TimedPlate {

	private Plate original;
	private int incept_date;
	
	public TimedPlateImpl(Plate plate, int rotation_count) {
		original = plate;
		incept_date = rotation_count;
	}

	@Override
	public Sushi getContents() {
		return original.getContents();
	}

	@Override
	public double getPrice() {
		return original.getPrice();
	}

	@Override
	public Color getColor() {
		return original.getColor();
	}

	@Override
	public double getProfit() {
		return original.getProfit();
	}

	@Override
	public int getInceptDate() {
		return incept_date;
	}

	@Override
	public Plate getOriginal() {
		return original;
	}

	@Override
	public Chef getChef() {
		return original.getChef();
	}
}
