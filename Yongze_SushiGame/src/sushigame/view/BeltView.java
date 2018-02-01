package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rawMaterials.Ingredient;
import rawMaterials.IngredientPortion;
import rawMaterials.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JLabel[] belt_labels;
	private JButton[] belt_buttons;

	public BeltView(Belt b) {
		this.belt = b;
		this.setLayout(new GridLayout(belt.getSize() / 2, 2));
		belt.registerBeltObserver(this);
		belt_buttons = new JButton[belt.getSize()];
		belt_labels = new JLabel[belt.getSize()];
		BeltListener listener = new BeltListener(b);
		for (int i = 0; i < belt.getSize(); i++) {
			JButton button = new JButton();
			button.setBackground(Color.white);
			button.setActionCommand(String(i));
			button.setText("Position " + (i + 1) + " is Empty Now");
			this.add(button);
			belt_buttons[i] = button;
			belt_buttons[i].addActionListener(listener);
		}
		refresh();
	}

	private String String(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		refresh();
	}

	public void refresh() {
		for (int i = 0; i < belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JButton button = belt_buttons[i];
			if (p != null) {
				switch (p.getColor()) {
				case RED:
					button.setBackground(Color.RED);
					break;
				case GREEN:
					button.setBackground(Color.GREEN);
					break;
				case BLUE:
					button.setBackground(new Color(100, 125, 230));
					break;
				case GOLD:
					button.setBackground(Color.YELLOW);
					break;
				}
				if (p.getContents().getName().contains("sashimi")) {
					button.setText("position " + (i + 1) + ": " + p.getContents().getName());
				} else if (p.getContents().getName().contains("nigiri")) {
					button.setText("position " + (i + 1) + ": " + p.getContents().getName());
				} else {
					button.setText("position " + (i + 1) + " " + p.getContents().getName());
				}
			} else {
				button.setBackground(Color.white);
				button.setText("Position " + (i + 1) + " is Empty Now");
			}
			button.setActionCommand(i + "");
		}
	}

	private JButton[] getButtons() {
		return belt_buttons;
	}
}

class BeltListener implements ActionListener {
	private Belt belt;

	public BeltListener(Belt b) {
		belt = b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < belt.getSize(); i++) {
			if (e.getActionCommand().equals(i + "")) {
				if (belt.getPlateAtPosition(i) != null){
					if (belt.getPlateAtPosition(i).getContents().getName().contains("sashimi")
							|| belt.getPlateAtPosition(i).getContents().getName().contains("nigiri")) {
						String msg = "Age: " + belt.getAgeOfPlateAtPosition(i) + " \nChef: "
								+ belt.getPlateAtPosition(i).getChef().getName();
						JOptionPane.showMessageDialog(null, msg);
					} else if (belt.getPlateAtPosition(i).getContents().getName().contains("Roll")) {
						String msg = "Age: " + belt.getAgeOfPlateAtPosition(i) + " \nChef: "
								+ belt.getPlateAtPosition(i).getChef().getName();
						String mlg = "";
						for (IngredientPortion a : belt.getPlateAtPosition(i).getContents().getIngredients()) {
							mlg += "" + a.getName() + ": " + Math.round(a.getAmount() * 1000.d) / 1000.d + " ounces\n";
						}
						JOptionPane.showMessageDialog(null, msg + "\n" + "Ingredient list below: \n" + mlg);
					}
				}
			}
		}
	}
}
