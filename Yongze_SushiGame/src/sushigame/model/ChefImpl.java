package sushigame.model;

import java.util.ArrayList;
import java.util.List;

import rawMaterials.IngredientPortion;
import rawMaterials.Plate;
import rawMaterials.RedPlate;
import rawMaterials.Sushi;
import rawMaterials.Nigiri.NigiriType;
import rawMaterials.Plate.Color;
import rawMaterials.Sashimi.SashimiType;

public class ChefImpl implements Chef, BeltObserver {

	private double balance;
	private List<HistoricalPlate> plate_history;
	private String name;
	private ChefsBelt belt;
	private boolean already_placed_this_rotation;
	private double food_sold, food_spoiled;
	
	public ChefImpl(String name, double starting_balance, ChefsBelt belt) {
		this.name = name;
		this.balance = starting_balance;
		this.food_sold = 0;
		this.food_spoiled = 0;
		this.belt = belt;
		belt.registerBeltObserver(this);
		already_placed_this_rotation = false;
		plate_history = new ArrayList<HistoricalPlate>();
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public HistoricalPlate[] getPlateHistory(int history_length) {
		if (history_length < 1 || (plate_history.size() == 0)) {
			return new HistoricalPlate[0];
		}

		if (history_length > plate_history.size()) {
			history_length = plate_history.size();
		}
		return plate_history.subList(plate_history.size()-history_length, plate_history.size()-1).toArray(new HistoricalPlate[history_length]);
	}

	@Override
	public HistoricalPlate[] getPlateHistory() {
		return getPlateHistory(plate_history.size());
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void makeAndPlacePlate(Plate plate, int position) 
			throws InsufficientBalanceException, BeltFullException, AlreadyPlacedThisRotationException {

		if (already_placed_this_rotation) {
			throw new AlreadyPlacedThisRotationException();
		}
		
		if (plate.getContents().getCost() > balance) {
			throw new InsufficientBalanceException();
		}
		belt.setPlateNearestToPosition(plate, position);
		balance = balance - plate.getContents().getCost();
		already_placed_this_rotation = true;
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.PLATE_CONSUMED) {
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this) {
				balance += plate.getPrice();
				Customer consumer = belt.getCustomerAtPosition(((PlateEvent) e).getPosition());
				plate_history.add(new HistoricalPlateImpl(plate, consumer));
				plateSold(new HistoricalPlateImpl(plate,consumer));
			}
		} else if (e.getType() == BeltEvent.EventType.PLATE_SPOILED) {
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this)	plateSpoiled(new HistoricalPlateImpl(plate, null));
			
			plate_history.add(new HistoricalPlateImpl(plate, null));
		} else if (e.getType() == BeltEvent.EventType.ROTATE) {
			already_placed_this_rotation = false;
		}
	}
	
	@Override
	public boolean alreadyPlacedThisRotation() {
		return already_placed_this_rotation;
	}

	@Override
	public double getFoodSold() {
		return Math.round(food_sold*100.0)/100.0;
	}

	@Override
	public double getFoodSpoiled() {
		return Math.round(food_spoiled*100.0)/100.0;
	}
	
	public void plateSpoiled(HistoricalPlate plate){
		if (plate.wasSpoiled() && plate.getChef() == this){
			for (int i=0; i <plate.getContents().getIngredients().length; i++) {
				food_spoiled += plate.getContents().getIngredients()[i].getAmount();
			}
		}
	}
	public void plateSold(HistoricalPlate plate){
		if (plate.wasSpoiled() == false){
			if (plate.getConsumer() != null){
				for (int i=0; i <plate.getContents().getIngredients().length; i++) {
					food_sold += plate.getContents().getIngredients()[i].getAmount();
				}
			}
		}
	}
}