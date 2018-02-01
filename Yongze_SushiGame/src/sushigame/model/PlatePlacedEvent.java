package sushigame.model;

import rawMaterials.Plate;

public class PlatePlacedEvent extends PlateEvent {

	public PlatePlacedEvent (Plate p, int position) {
		super(BeltEvent.EventType.PLATE_PLACED, p, position);
	}
}
