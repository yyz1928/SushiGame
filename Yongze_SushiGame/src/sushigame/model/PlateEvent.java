package sushigame.model;


import rawMaterials.Plate;

abstract public class PlateEvent extends BeltEvent {
	private Plate plate;
	private int position;
	
	public PlateEvent(BeltEvent.EventType type, Plate plate, int position) {
		super(type);
		this.plate = plate;
		this.position = position;
	}
	
	public  Plate getPlate() {
		return plate;
	}
	
	public int getPosition() {
		return position;
	}
}