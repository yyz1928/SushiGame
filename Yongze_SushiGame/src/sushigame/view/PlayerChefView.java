package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import rawMaterials.*;
import rawMaterials.Nigiri.NigiriType;
import rawMaterials.Sashimi.SashimiType;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;
	private JSlider price_slider;
	private JSlider[] ingredient_sliders;
	private JButton make_roll, make_sashimi, make_nigiri;
	private static final int NUM_OF_INGREDIENTS = 8;
	private JComboBox<String> ingredient_list1, ingredient_list2, plate_color_list;
	private JSlider plate_pos;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(0,2,10,10));

		//New Code

		String[] ingredient_names = new String[] {"Crab", "Eel", "Salmon", "Shrimp", "Tuna"};
		String[] plate_colors = new String[] {"Blue", "Green", "Red", "Gold"};

		String html_font = "<html><h1 style=\"font-size: 14\">";  //HTML formatting of text
		String close_html_font = "</h1></html>";

		add(new JLabel(html_font + "Plate Color: ")); 

		plate_color_list = new JComboBox<>(plate_colors);
		ingredient_list1 = new JComboBox<>(ingredient_names);
		ingredient_list2 = new JComboBox<>(ingredient_names);

		add(plate_color_list); //Adds the Plate Color dropdown list


		Hashtable<Integer, JLabel> table1 = new Hashtable<Integer, JLabel>();
		table1.put(new Integer(0), new JLabel("0.0"));
		//		table1.put(new Integer(25), new JLabel(".25"));
		table1.put(new Integer(50), new JLabel(".50"));
		//		table1.put(new Integer(75), new JLabel(".75"));
		table1.put(new Integer(100), new JLabel("1.00"));
		//		table1.put(new Integer(125), new JLabel("1.25"));
		table1.put(new Integer(150), new JLabel("1.50"));

		ingredient_sliders = new JSlider[NUM_OF_INGREDIENTS];

		price_slider = new JSlider(500, 1000, 500);
		for (int i=0; i < NUM_OF_INGREDIENTS; i++){
			ingredient_sliders[i] = new JSlider(0, 150, 0);
			ingredient_sliders[i].setLabelTable(table1);
			ingredient_sliders[i].setPaintTicks(true);
			ingredient_sliders[i].setPaintLabels(true);
			ingredient_sliders[i].setMajorTickSpacing(25);
		}

		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put(new Integer(500), new JLabel("$5.00"));
		table.put(new Integer(600), new JLabel("$6.00"));
		table.put(new Integer(700), new JLabel("$7.00"));
		table.put(new Integer(800), new JLabel("$8.00"));
		table.put(new Integer(900), new JLabel("$9.00"));
		table.put(new Integer(1000), new JLabel("$10.00"));
		price_slider.setLabelTable(table);

		price_slider.setPaintTicks(true);
		price_slider.setPaintLabels(true);
		price_slider.setMajorTickSpacing(100);


		add(new JLabel(html_font + "Gold Plate Price: "));
		add(price_slider);

		//Plate Position
		plate_pos = new JSlider(1,belt_size);
		plate_pos.setPaintTicks(true);
		plate_pos.setSnapToTicks(true);
		plate_pos.setMajorTickSpacing(1);
//		plate_pos = new JTextField("0", 20);
//		plate_pos.setToolTipText(html_font + "Please enter a position" + close_html_font);
		add(new JLabel(html_font + "Plate Position (Belt size: " + belt_size + "): " + close_html_font));
		add(plate_pos);

		//Sashimi and Nigiri Buttons and Lists
		make_sashimi = new JButton(html_font + "Make Sashimi" + close_html_font);
		make_sashimi.setActionCommand("make_sashimi");
		make_sashimi.addActionListener(this);

		make_nigiri = new JButton(html_font + "Make Nigiri" + close_html_font);
		make_nigiri.setActionCommand("make_nigiri");
		make_nigiri.addActionListener(this);

		add(new JLabel(html_font + "Sashimi Type: " + close_html_font));
		add(ingredient_list1);

		add(new JLabel(html_font + "Nigiri Type: " + close_html_font));
		add(ingredient_list2);

		add(make_sashimi);
		add(make_nigiri);

		//Make the Sliders

		add(new JLabel(html_font + "These sliders are for Rolls only (0.00 to 1.50 oz)" + close_html_font));
		add(new JLabel(""));
		add(new JLabel(html_font + "Avocado: " + close_html_font));
		add(ingredient_sliders[0]);
		add(new JLabel(html_font + "Crab: " + close_html_font));
		add(ingredient_sliders[1]);
		add(new JLabel(html_font + "Eel: " + close_html_font));
		add(ingredient_sliders[2]);
		add(new JLabel(html_font + "Rice: " + close_html_font));
		add(ingredient_sliders[3]);
		add(new JLabel(html_font + "Salmon: " + close_html_font));
		add(ingredient_sliders[4]);
		add(new JLabel(html_font + "Seaweed: " + close_html_font));
		add(ingredient_sliders[5]);
		add(new JLabel(html_font + "Shrimp: " + close_html_font));
		add(ingredient_sliders[6]);
		add(new JLabel(html_font + "Tuna: " + close_html_font));
		add(ingredient_sliders[7]);

		make_roll = new JButton("Make Roll"); //Roll Button
		make_roll.setActionCommand("make_roll");
		make_roll.addActionListener(this);
		add(make_roll, BorderLayout.EAST);
		//End of New Code

	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String plate_color = plate_color_list.getSelectedItem().toString();
		String s_type = ingredient_list1.getSelectedItem().toString();
		String n_type = ingredient_list2.getSelectedItem().toString();
		SashimiType sashimi_type = null;
		NigiriType nigiri_type = null;

		switch (s_type){
		case "Crab": sashimi_type = SashimiType.CRAB;
		break;
		case "Eel": sashimi_type = SashimiType.EEL;
		break;
		case "Salmon": sashimi_type = SashimiType.SALMON;
		break;
		case "Shrimp": sashimi_type = SashimiType.SHRIMP;
		break;
		case "Tuna": sashimi_type = SashimiType.TUNA;
		break;
		}

		switch (n_type){
		case "Crab": nigiri_type = NigiriType.CRAB;
		break;
		case "Eel": nigiri_type = NigiriType.EEL;
		break;
		case "Salmon": nigiri_type = NigiriType.SALMON;
		break;
		case "Shrimp": nigiri_type = NigiriType.SHRIMP;
		break;
		case "Tuna": nigiri_type = NigiriType.TUNA;
		break;
		}

		for (int i=0; i < SashimiType.values().length; i++){
			if (s_type.equalsIgnoreCase(SashimiType.values()[i].name())) sashimi_type = SashimiType.values()[i];

			if (n_type.equalsIgnoreCase(NigiriType.values()[i].name())) nigiri_type = NigiriType.values()[i];
		}

		//make an array of int values for each ingredient and then pass the ingredient array to the Request
		//perhaps changing the method so it takes an array
		double[] ing_values = new double[NUM_OF_INGREDIENTS];
		double plate_price = price_slider.getValue()/100.0;
		int position = plate_pos.getValue() - 1;

//		if (plate_pos.getText() == null){
//			throw new IllegalArgumentException("You must choose a position for the plate");
//		} else{
//			position = Integer.parseInt(plate_pos.getText())-1;
//		}

		//ArrayList of Integers that hold the valid indices
		ArrayList<Integer> valid_portion = new ArrayList<>();
		for (int i=0; i < NUM_OF_INGREDIENTS; i++){
			if (ingredient_sliders[i].getValue()/100.0 > 0.0){
				valid_portion.add(i);
			}
		}
		
		
		IngredientPortion[] roll_ingr = new IngredientPortion[valid_portion.size()];

		for (int i=0; i < valid_portion.size(); i++){
			switch (valid_portion.get(i)){
			case 0: roll_ingr[i] = new AvocadoPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 1: roll_ingr[i] = new CrabPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 2: roll_ingr[i] = new EelPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 3: roll_ingr[i] = new RicePortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 4: roll_ingr[i] = new SalmonPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 5: roll_ingr[i] = new SeaweedPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 6: roll_ingr[i] = new ShrimpPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			case 7: roll_ingr[i] = new TunaPortion(ingredient_sliders[valid_portion.get(i)].getValue()/100.0);
					ingredient_sliders[valid_portion.get(i)].setValue(0);
			break;
			}
		}

		//Gives the array the IngredientPortions of the ArrayList roll_ingr
		//		for (IngredientPortion ingr : roll_ingr) {
		//			clone_ingr[j] = ingr;
		//			j++;
		//		}

		switch (e.getActionCommand()) {
		case "make_roll": 
			if (valid_portion.size() == 0) return;
			switch(plate_color){
			case "Gold": makeGoldPlateRequest(new Roll("Roll", roll_ingr), position, plate_price);
			break;
			case "Blue": makeBluePlateRequest(new Roll("Roll", roll_ingr), position);
			break;
			case "Green": makeGreenPlateRequest(new Roll("Roll", roll_ingr), position);
			break;
			case "Red": makeRedPlateRequest(new Roll("Roll", roll_ingr), position);
			break;
			}
			break;

		case "make_sashimi": 
			switch(plate_color){
			case "Gold": makeGoldPlateRequest(new Sashimi(sashimi_type), position, plate_price);
			break;
			case "Blue": makeBluePlateRequest(new Sashimi(sashimi_type), position);
			break;
			case "Green": makeGreenPlateRequest(new Sashimi(sashimi_type), position);
			break;
			case "Red": makeRedPlateRequest(new Sashimi(sashimi_type), position);
			break;
			}
			break;

		case "make_nigiri":
			switch(plate_color){
			case "Gold": makeGoldPlateRequest(new Nigiri(nigiri_type), position, plate_price);
			break;
			case "Blue": makeBluePlateRequest(new Nigiri(nigiri_type), position);
			break;
			case "Green": makeGreenPlateRequest(new Nigiri(nigiri_type), position);
			break;
			case "Red": makeRedPlateRequest(new Nigiri(nigiri_type), position);
			break;
			}
			break;
		}
	}
}
