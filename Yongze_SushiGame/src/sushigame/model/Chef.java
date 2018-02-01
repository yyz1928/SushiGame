package sushigame.model;

import rawMaterials.Plate;
import rawMaterials.Sushi;
import rawMaterials.Plate.Color;

public interface Chef {

	String getName();
	void setName(String name);
	
	void makeAndPlacePlate(Plate plate, int position) 
			throws InsufficientBalanceException, BeltFullException, AlreadyPlacedThisRotationException;
		
	HistoricalPlate[] getPlateHistory(int max_history_length);
	HistoricalPlate[] getPlateHistory();
	
	double getBalance();
	double getFoodSold();
	double getFoodSpoiled();
	
	boolean alreadyPlacedThisRotation();

}
