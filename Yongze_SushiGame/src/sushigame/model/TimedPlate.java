package sushigame.model;


import rawMaterials.Plate;

public interface TimedPlate extends Plate {
	int getInceptDate();
	Plate getOriginal();
}
