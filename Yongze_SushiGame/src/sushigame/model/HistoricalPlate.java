package sushigame.model;

import rawMaterials.Plate;

public interface HistoricalPlate extends Plate {

	boolean wasSpoiled();
	Customer getConsumer();
}
