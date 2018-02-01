package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private JButton update_scoreboard;
	private JComboBox<String> types_scoreboard;
	private int food_consumed;
	private int food_spoiled;
	private String scoreboard_type;
	private int button_press_count = 0;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new GridLayout(6,1));
		add(display, BorderLayout.CENTER);
		display.setText(makeBalanceScoreboardHTML());
		

		String[] scoreboard_options = new String[] {"Balance Order", "Food Sold Order", "Food Spoiled Order"};
		types_scoreboard = new JComboBox<>(scoreboard_options);
		add(types_scoreboard);

//		update_scoreboard = new JButton("Update Scoreboard");
//		update_scoreboard.setPreferredSize(new Dimension(100,100));
//		update_scoreboard.setActionCommand("update_scoreboard");
//		update_scoreboard.addActionListener(this);
//		add(update_scoreboard);
	}

//	private String makeScoreboardHTML() {
//		String sb_html = "<html>";
//		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";
//
//		// Create an array of all chefs and sort by balance.
//		Chef[] opponent_chefs= game_model.getOpponentChefs();
//		Chef[] chefs = new Chef[opponent_chefs.length+1];
//		chefs[0] = game_model.getPlayerChef();
//		for (int i=1; i<chefs.length; i++) {
//			chefs[i] = opponent_chefs[i-1];
//		}
//		
//		if (scoreboard_type == null) scoreboard_type = "Food Sold Order";
//		
//		switch (scoreboard_type) {
//		case "Balance Order": 
//			Arrays.sort(chefs, new HighToLowBalanceComparator());
//
//			for (Chef c : chefs) {
//				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
//			}
//			break;
//		case "Food Sold Order":
//			Arrays.sort(chefs, new HighToLowConsumedComparator());
//
//			for (Chef c : chefs) {
//				sb_html += c.getName() + " (Amount Consumed: " + c.getFoodSold() + ") <br>";
//			}
//			break;
//		case "Food Spoiled Order":
//			Arrays.sort(chefs, new LowToHighSpoiledComparator());
//
//			for (Chef c : chefs) {
//				sb_html += c.getName() + " (Amount Spoiled: " + c.getFoodSpoiled() + ") <br>";
//			}
//			break;
////		default:
////			Arrays.sort(chefs, new HighToLowBalanceComparator());
////
////			for (Chef c : chefs) {
////				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
////			}
////			break;
//		}
//
//		return sb_html;
//	}

	public void refresh() {
		switch (types_scoreboard.getSelectedIndex()){
		case 0: display.setText(makeBalanceScoreboardHTML());
		break;
		case 1: display.setText(makeFoodSoldScoreboardHTML());
		break;
		case 2: display.setText(makeFoodSpoiledScoreboardHTML());
		break;
		}
//		display.removeAll();
//		String text = makeScoreboardHTML() + "<br> Number of rotations: " + button_press_count;
//		display.setText(text);
//		
//		display.repaint();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		} 
//		else if (e.getType() == BeltEvent.EventType.PLATE_CONSUMED) {
//			refresh();
//		} else if (e.getType() == BeltEvent.EventType.PLATE_SPOILED) {
//			refresh();
//		}

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		//		 scoreboard_type = types_scoreboard.getSelectedItem().toString();
		
//		if (ae.equals("update_scoreboard")) {
//			refresh();
////			button_press_count++;
//		} 
	}
	
	public String makeBalanceScoreboardHTML(){
		String sb_html = "<html>";
		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		
		Arrays.sort(chefs, new HighToLowBalanceComparator());

		for (Chef c : chefs) {
			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
		}
		
		return sb_html;

	}
	
	public String makeFoodSoldScoreboardHTML(){
		String sb_html = "<html>";
		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		
		Arrays.sort(chefs, new HighToLowConsumedComparator());

		for (Chef c : chefs) {
			sb_html += c.getName() + " (Amount Consumed: " + c.getFoodSold() + ") <br>";
		}
		
		return sb_html;
	}
	
	public String makeFoodSpoiledScoreboardHTML(){
		String sb_html = "<html>";
		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		
		Arrays.sort(chefs, new LowToHighSpoiledComparator());

		for (Chef c : chefs) {
			sb_html += c.getName() + " (Amount Spoiled: " + c.getFoodSpoiled() + ") <br>";
		}
		
		return sb_html;

	}
//	public void setScoreboardType(String t){
//		scoreboard_type = t;
//	}
//	
//	public String getScoreboardType(){
//		return scoreboard_type;
//	}

	//	private String scoreboardBalanceOrder(){
	//		String sb_html = "<html>";
	//		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";
	//
	//		// Create an array of all chefs and sort by balance.
	//		Chef[] opponent_chefs= game_model.getOpponentChefs();
	//		Chef[] chefs = new Chef[opponent_chefs.length+1];
	//		chefs[0] = game_model.getPlayerChef();
	//		for (int i=1; i<chefs.length; i++) {
	//			chefs[i] = opponent_chefs[i-1];
	//		}
	//		Arrays.sort(chefs, new HighToLowBalanceComparator());
	//
	//		for (Chef c : chefs) {
	//			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
	//		}
	//		return sb_html;
	//	}
	//	private String scoreboardFoodSoldOrder(){
	//		String sb_html = "<html>";
	//		sb_html += "<h1 style=\"color: blue;text-align: center; font-size: 22\">Scoreboard</h1>";
	//		
	//		for (Chef c : chefs) {
	//			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
	//		}
	//	}
	//	private String scoreboardFoodSpoiledOrder(){
	//		
	//	}

}
