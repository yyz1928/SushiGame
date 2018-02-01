package sushigame.model;

import rawMaterials.Plate;

interface ChefsBelt extends Belt {

	int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException;

	
}
