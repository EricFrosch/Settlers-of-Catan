package catanGameModified;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Button;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RunCatanDisplay extends JPanel implements ActionListener, KeyListener{
	
	private JFrame frame;
	private Timer timer;
	private CatanBoard cb = new CatanBoard(500, 350);
	private int xyz = -1; // turn number
	//private CatanPlayerAI p1 = new CatanPlayerAI(Color.blue);
	private CatanPlayerHuman p1 = new CatanPlayerHuman(Color.BLUE);
	private CatanPlayerAI p2 = new CatanPlayerAI(Color.red);
	private CatanPlayerAI p3 = new CatanPlayerAI(Color.orange);
	private CatanPlayerAI p4 = new CatanPlayerAI(Color.black);
	private JButton[] vertexButtons = new JButton[cb.getVertices().length];{
	for(int vb = 0; vb < cb.getVertices().length; vb++) {
		vertexButtons[vb] = new JButton();
	}}
	private JButton[] pathButtons = new JButton[cb.getPaths().length]; {
	for(int pb = 0; pb < pathButtons.length; pb++) {
		pathButtons[pb] = new JButton();
	}}
	private JButton endTurn = new JButton("End Turn");
	private JButton buildRoad = new JButton("Build Road");
	private JButton buildSettlement = new JButton("Build Settlement");
	private JButton buildCity = new JButton("Build City");
	private JButton trade = new JButton("Trade");
	private JButton wood = new JButton("Wood");
	private JButton bricks = new JButton("Bricks");
	private JButton wheat = new JButton("Wheat");
	private JButton sheep = new JButton("Sheep");
	private JButton ore = new JButton("Ore");
	private Graphics g;
	private String wxy;
	private String message = "Build a settlement and a road, then click End Turn";
	private String tradeAway;
	
	public RunCatanDisplay() {
		frame = new JFrame("Catan Board");
		frame.setSize(1300, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.add(this);	
		frame.setVisible(true);
		//the game can run 2 ways; either on a timer, or by pressing the 'a' key to have the next bot in line take a turn
		
		frame.addKeyListener(this);
		
		//timer = new Timer(1000, (ActionListener) this);
		//timer.start();
		for(int vb = 0; vb < vertexButtons.length; vb++) {
			vertexButtons[vb] = new JButton();
		}
		// creates the buttons on the vertices
		String actionCommand = "";
		for(Integer vb = 0; vb < vertexButtons.length; vb++) {
			vertexButtons[vb].setBounds(cb.getVertices(vb).getxCoord()-5, 
					cb.getVertices(vb).getyCoord()-5, 10, 10);
			vertexButtons[vb].addActionListener(this);
			actionCommand = "v" + vb.toString();
			vertexButtons[vb].setActionCommand(actionCommand);
			add(vertexButtons[vb]);
			vertexButtons[vb].setBackground(Color.WHITE);
		}
		
		// creates the buttons on the paths
		actionCommand = "";
		for(Integer pb = 0; pb < pathButtons.length; pb++) {
			pathButtons[pb].setBounds((cb.getPaths(pb).getV1().getxCoord() + cb.getPaths(pb).getV2().getxCoord())/2-5, 
					(cb.getPaths(pb).getV1().getyCoord() + cb.getPaths(pb).getV2().getyCoord())/2-5, 10, 10);
			pathButtons[pb].addActionListener(this);
			actionCommand = "p" + pb.toString();
			pathButtons[pb].setActionCommand(actionCommand);
			add(pathButtons[pb]);
			pathButtons[pb].setBackground(Color.WHITE);
		}
		
		// creates the buttons for building and trading
		// creates end turn button
		endTurn.setBounds(1000, 150, 100, 50);
		endTurn.addActionListener(this);
		endTurn.setActionCommand("End Turn");
		add(endTurn);
		endTurn.setBackground(Color.WHITE);
		
		// creates build road button
		buildRoad.setBounds(1000, 210, 100, 50);
		buildRoad.addActionListener(this);
		buildRoad.setActionCommand("Build Road");
		add(buildRoad);
		buildRoad.setBackground(Color.WHITE);

		// build settlement
		buildSettlement.setBounds(1000, 270, 100, 50);
		buildSettlement.addActionListener(this);
		buildSettlement.setActionCommand("Build Settlement");
		add(buildSettlement);
		buildSettlement.setBackground(Color.WHITE);

		// build city
		buildCity.setBounds(1000, 330, 100, 50);
		buildCity.addActionListener(this);
		buildCity.setActionCommand("Build City");
		add(buildCity);
		buildCity.setBackground(Color.WHITE);
		
		// trade
		trade.setBounds(1000, 390, 100, 50);
		trade.addActionListener(this);
		trade.setActionCommand("Trade");
		add(trade);
		trade.setBackground(Color.WHITE);
		
		// resource buttons
		// wood
		wood.setBounds(1000, 500, 75, 25);
		wood.addActionListener(this);
		wood.setActionCommand("Wood");
		add(wood);
		wood.setBackground(Color.WHITE);
		
		// bricks
		bricks.setBounds(1000, 530, 75, 25);
		bricks.addActionListener(this);
		bricks.setActionCommand("Bricks");
		add(bricks);
		bricks.setBackground(Color.WHITE);
		
		// wheat
		wheat.setBounds(1000, 560, 75, 25);
		wheat.addActionListener(this);
		wheat.setActionCommand("Wheat");
		add(wheat);
		wheat.setBackground(Color.WHITE);
		
		// sheep
		sheep.setBounds(1000, 590, 75, 25);
		sheep.addActionListener(this);
		sheep.setActionCommand("Sheep");
		add(sheep);
		sheep.setBackground(Color.WHITE);
		
		// ore
		ore.setBounds(1000, 620, 75, 25);
		ore.addActionListener(this);
		ore.setActionCommand("Ore");
		add(ore);
		ore.setBackground(Color.WHITE);
		
		// disables buttons
		buildRoad.setEnabled(false);
		buildSettlement.setEnabled(false);
		buildCity.setEnabled(false);
		trade.setEnabled(false);
		wood.setEnabled(false);
		bricks.setEnabled(false);
		wheat.setEnabled(false);
		sheep.setEnabled(false);
		ore.setEnabled(false);
	}
	
	public void playersBuild(Graphics g) {
		
		//builds each bot's first settlement
		if (xyz == -1) {
		
		p2.buildFirstSettlement(g, cb, p2.lookForBestVertex(cb), p2.lookForBestPathPath(cb, p2.lookForBestVertex(cb)));
		p3.buildFirstSettlement(g, cb, p3.lookForBestVertex(cb), p3.lookForBestPathPath(cb, p3.lookForBestVertex(cb)));
		p4.buildFirstSettlement(g, cb, p4.lookForBestVertex(cb), p4.lookForBestPathPath(cb, p4.lookForBestVertex(cb)));
		p4.buildFirstSettlement(g, cb, p4.lookForBestVertex(cb), p4.lookForBestPathPath(cb, p4.lookForBestVertex(cb)));
		p3.buildFirstSettlement(g, cb, p3.lookForBestVertex(cb), p3.lookForBestPathPath(cb, p3.lookForBestVertex(cb)));
		p2.buildFirstSettlement(g, cb, p2.lookForBestVertex(cb), p2.lookForBestPathPath(cb, p2.lookForBestVertex(cb)));
		// enables the path and vertex buttons
		for(int pb = 0; pb < pathButtons.length; pb++) {
			pathButtons[pb].setEnabled(true);
		}
		for(int vb = 0; vb < vertexButtons.length; vb++) {
			vertexButtons[vb].setEnabled(true);
		}
		message = "Build a second settlement and road, then click End Turn to start the game";
		p1.addWood(2);
		p1.addBrick(2);
		p1.addWheat(1);
		p1.addSheep(1);
		}
		else if(xyz >= 0) {
		//then roll the dice and the next bot takes a turn
		int rolled = resourceRoll(cb,p1,p2,p3,p4);
		if (xyz % 4 == 0) { 
			// enable buttons when it is your turn
			buildRoad.setEnabled(true);
			buildRoad.setBackground(Color.BLUE);
			buildSettlement.setEnabled(true);
			buildCity.setEnabled(true);
			trade.setEnabled(true);
		}
		else {
			// disable buttons when turn is ended if it isn't your turn
			buildRoad.setEnabled(false);
			buildSettlement.setEnabled(false);
			buildCity.setEnabled(false);
			trade.setEnabled(false);
			wood.setEnabled(false);
			bricks.setEnabled(false);
			wheat.setEnabled(false);
			sheep.setEnabled(false);
			ore.setEnabled(false);
			for(int pb = 0; pb < pathButtons.length; pb++) {
				pathButtons[pb].setEnabled(false);
			}
			for(int vb = 0; vb < vertexButtons.length; vb++) {
				vertexButtons[vb].setEnabled(false);
			}
		}
		if (xyz % 4 == 1) { p2.takeTurn(g, cb); }
		if (xyz % 4 == 2) { p3.takeTurn(g, cb); }
		if (xyz % 4 == 3) { p4.takeTurn(g, cb); }
		//System.out.println(xyz);
		wxy = "" + rolled;
		//win conditions; a square of the winner's color is drawn if a player meets the win condition
		if (p1.getVP() >= 10) { g.setColor(p1.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p2.getVP() >= 10) { g.setColor(p2.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p3.getVP() >= 10) { g.setColor(p3.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p4.getVP() >= 10) { g.setColor(p4.getColor()); g.fillRect(450, 300, 100, 100); }
		}
		xyz++;
	}
	
	public void paint(Graphics g) {
		this.g = g;
		//redraws the game board
		cb.drawGameBoard(g);
		for (int k = 0; k < cb.getVertices().length; k++) {
			if (cb.getVertices(k).getSettlement() != Color.white) {
				cb.getVertices(k).drawSettlement(g, cb.getVertices(k).getSettlement());
				if (cb.getVertices(k).getIsCity() == true) {
					cb.getVertices(k).drawCity(g, cb.getVertices(k).getSettlement());
				}
			}
		}
		for (int k = 0; k < cb.getPaths().length; k++) {
			if (cb.getPaths(k).getRoad() != null) {
				cb.getPaths(k).drawRoad(g, cb.getPaths(k).getRoad());
			}
		}
		
		// records the current state of each button before the button is overwritten
		boolean buttonState;
		for(int vb = 0; vb < vertexButtons.length; vb++) {
			if(vertexButtons[vb] != null) {buttonState = vertexButtons[vb].isEnabled();}
			else {buttonState = true;}
			vertexButtons[vb].setBackground(Color.WHITE);
			vertexButtons[vb].setEnabled(buttonState);
		}
		
		for(Integer pb = 0; pb < pathButtons.length; pb++) {
			if(pathButtons[pb] != null) {buttonState = pathButtons[pb].isEnabled();}
			else {buttonState = true;}
			pathButtons[pb].setBackground(Color.WHITE);
			pathButtons[pb].setEnabled(buttonState);
		}
		
		if(endTurn != null) {buttonState = endTurn.isEnabled();}
		else {buttonState = true;}
		// instantiates and creates the end turn button
		endTurn.setBackground(Color.WHITE);
		endTurn.setEnabled(buttonState);
		
		//
		if(buildRoad != null) {buttonState = buildRoad.isEnabled();}
		else {buttonState = true;}
		buildRoad.setBackground(Color.WHITE);
		buildRoad.setEnabled(buttonState);
		
		if(buildSettlement != null) {buttonState = buildSettlement.isEnabled();}
		else {buttonState = true;}
		buildSettlement.setBackground(Color.WHITE);
		buildSettlement.setEnabled(buttonState);
		
		if(buildCity != null) {buttonState = buildCity.isEnabled();}
		else {buttonState = true;}
		buildCity.setBackground(Color.WHITE);
		buildCity.setEnabled(buttonState);
		
		if(trade != null) {buttonState = trade.isEnabled();}
		else {buttonState = true;}
		trade.setBackground(Color.WHITE);
		trade.setEnabled(buttonState);
		
		if(wood != null) {buttonState = wood.isEnabled();}
		else {buttonState = true;}
		wood.setBackground(Color.WHITE);
		wood.setEnabled(buttonState);
		
		if(bricks != null) {buttonState = bricks.isEnabled();}
		else {buttonState = true;}
		bricks.setBackground(Color.WHITE);
		bricks.setEnabled(buttonState);
		
		if(wheat != null) {buttonState = wheat.isEnabled();}
		else {buttonState = true;}
		wheat.setBackground(Color.WHITE);
		wheat.setEnabled(buttonState);
		
		if(sheep != null) {buttonState = sheep.isEnabled();}
		else {buttonState = true;}
		sheep.setBackground(Color.WHITE);
		sheep.setEnabled(buttonState);
		
		if(ore != null) {buttonState = ore.isEnabled();}
		else {buttonState = true;}
		ore.setBackground(Color.WHITE);
		ore.setEnabled(buttonState);
		
		if(wxy != null) {
			g.setColor(Color.black);
			g.drawString(wxy, 15 * (xyz / 60), 10 + (xyz * 10) - (60 * 10 * (xyz / 60))+50);
		}
		
		//clears the area where the message will be drawn
		g.setColor(Color.WHITE);
		g.fillRect(875, 100, 500, 30);
		// draws message
		g.setColor(Color.black);
		g.drawString(message, 875, 125);
		// clears message variable after the message is drawn unless trade is taking place
		if(!(message.equals("Click on a resource that you want to trade away")) && (xyz >= 0)) {
			message = "";
		}
		
		// clears the area where the resource quantities will be drawn
		g.setColor(Color.WHITE);
		g.fillRect(1080, 500, 50, 20);
		g.fillRect(1080, 530, 50, 20);
		g.fillRect(1080, 560, 50, 20);
		g.fillRect(1080, 590, 50, 20);
		g.fillRect(1080, 620, 50, 20);
		// draws your resource quantities 
		g.setColor(Color.BLACK);
		g.drawString(("" + p1.getWood().getAmount()), 1080, 515);
		g.drawString(("" + p1.getBrick().getAmount()), 1080, 545);
		g.drawString(("" + p1.getWheat().getAmount()), 1080, 575);
		g.drawString(("" + p1.getSheep().getAmount()), 1080, 605);
		g.drawString(("" + p1.getOre().getAmount()), 1080, 635);
		
		// this rectangle just covers up a problem
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 100, 50);
		// draws the winning color in the middle of the board
		if (p1.getVP() >= 10) { g.setColor(p1.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p2.getVP() >= 10) { g.setColor(p2.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p3.getVP() >= 10) { g.setColor(p3.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p4.getVP() >= 10) { g.setColor(p4.getColor()); g.fillRect(450, 300, 100, 100); }
		
		// clears an area
		g.setColor(Color.WHITE);
		g.fillRect(800, 0, 400, 60);
		// each of the turn numbers are offset, because this is drawn after the turn number is updated
		if((xyz > 0) && (xyz % 4 == 1)) {
			g.setColor(Color.BLUE);
			g.fillRect(800, 30, 75, 25);
			g.drawString("Current Player", 800, 30);
		}
		else {
			g.setColor(Color.BLUE);
			g.drawRect(800, 30, 75, 25);
		}
		if((xyz > 0) && (xyz % 4 == 2)) {
			g.setColor(Color.RED);
			g.fillRect(900, 30, 75, 25);
			g.drawString("Current Player", 900, 30);
		}
		else {
			g.setColor(Color.RED);
			g.drawRect(900, 30, 75, 25);
		}
		if((xyz > 0) && (xyz % 4 == 3)) {
			g.setColor(Color.ORANGE);
			g.fillRect(1000, 30, 75, 25);
			g.drawString("Current Player", 1000, 30);
		}
		else {
			g.setColor(Color.ORANGE);
			g.drawRect(1000, 30, 75, 25);
		}
		if((xyz > 0) && (xyz % 4 == 0)) {
			g.setColor(Color.BLACK);
			g.fillRect(1100, 30, 75, 25);
			g.drawString("Current Player", 1100, 30);
		}
		else {
			g.setColor(Color.BLACK);
			g.drawRect(1100, 30, 75, 25);
		}
	}
	
	public static void main(String[] args){
		new RunCatanDisplay();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (p1.getVP() >= 10 || p2.getVP() >= 10 || p3.getVP() >= 10 || p4.getVP() >= 10) { return; }
		else if (e.getActionCommand().substring(0, 1).equals("v")) {
			if(cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getSettlement().equals(Color.BLUE) &&
					!(cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getIsCity())) {
				if((p1.getWheat().getAmount() < 2) || (p1.getOre().getAmount() < 3)) {
					message = "You don't have the resources for this";
				}
				p1.buildCity(g, cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))));
				}
			else if(cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getSettlement().equals(Color.WHITE)) {
				if((p1.getWood().getAmount() < 1) || (p1.getBrick().getAmount() < 1) || 
						(p1.getSheep().getAmount() < 1) || (p1.getWheat().getAmount() < 1)) {
					message = "You don't have the resources for this";
				}
				else if(!(p1.checkSettlementEligibility(cb, cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1)))))) {
					message = "This vertex is inelligible for a settlement";
				}
				p1.buildSettlement(g, cb, cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))));
				if(xyz <= 0) {
					for (int k = 0; k < cb.getHexagons().length; k++) {
						if (cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getHex1() == cb.getHexagons(k).getName()) {
							p1.addResource(cb.getHexagons(k).getResource(), 1);
						}
						else if (cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getHex2() == cb.getHexagons(k).getName()) {
							p1.addResource(cb.getHexagons(k).getResource(), 1);
						}
						else if (cb.getVertices(Integer.valueOf(e.getActionCommand().substring(1))).getHex3() == cb.getHexagons(k).getName()) {
							p1.addResource(cb.getHexagons(k).getResource(), 1);
						}
					}
				}
			}
			for(int vb = 0; vb < vertexButtons.length; vb++) {
				vertexButtons[vb].setEnabled(false);
			}
		}
		else if (e.getActionCommand().substring(0, 1).equals("p")) {
			p1.buildRoad(g, cb.getPaths(Integer.valueOf(e.getActionCommand().substring(1))));
			for(int pb = 0; pb < pathButtons.length; pb++) {
				pathButtons[pb].setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals("End Turn")) {
			playersBuild(g);
		}
		else if(e.getActionCommand().equals("Build Road")) {
			for(int pb = 0; pb < pathButtons.length; pb++) {
				pathButtons[pb].setEnabled(true);
			}
		}
		else if(e.getActionCommand().equals("Build Settlement")) {
			for(int vb = 0; vb < vertexButtons.length; vb++) {
				// this enables only the vertices with no settlements
				if(!(cb.getVertices(vb).getSettlement().equals(Color.WHITE))) {
					vertexButtons[vb].setEnabled(false);
				}
				else {
					vertexButtons[vb].setEnabled(true);
				}
			}
		}
		else if(e.getActionCommand().equals("Build City")) {
			// This enables only the vertices that already have blue settlements on them
			for(int vb = 0; vb < vertexButtons.length; vb++) {
				if(cb.getVertices(vb).getSettlement().equals(Color.BLUE)) {
					vertexButtons[vb].setEnabled(true);
				}
			}
		}
		else if(e.getActionCommand().equals("Trade")) {
			message = "Click on a resource that you want to trade away";
			wood.setEnabled(true);
			bricks.setEnabled(true);
			wheat.setEnabled(true);
			sheep.setEnabled(true);
			ore.setEnabled(true);
		}
		else if(e.getActionCommand().equals("Wood")) {
			if(message.equals("Click on a resource that you want to trade away")) {
				tradeAway = "Wood";
				message = "Click on a resource that you want to trade for";
				wood.setEnabled(false);
			}
			else {
				if(!(p1.trade(cb, tradeAway, "Wood"))) {
					message = "You do not have enough resources to trade away";
				}
				wood.setEnabled(false);
				bricks.setEnabled(false);
				wheat.setEnabled(false);
				sheep.setEnabled(false);
				ore.setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals("Bricks")) {
			if(message.equals("Click on a resource that you want to trade away")) {
				tradeAway = "Brick";
				message = "Click on a resource that you want to trade for";
				bricks.setEnabled(false);
			}
			else {
				if(!(p1.trade(cb, tradeAway, "Brick"))) {
					message = "You do not have enough resources to trade away";
				}
				wood.setEnabled(false);
				bricks.setEnabled(false);
				wheat.setEnabled(false);
				sheep.setEnabled(false);
				ore.setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals("Wheat")) {
			if(message.equals("Click on a resource that you want to trade away")) {
				tradeAway = "Wheat";
				message = "Click on a resource that you want to trade for";
				wheat.setEnabled(false);
			}
			else {
				if(!(p1.trade(cb, tradeAway, "Wheat"))) {
					message = "You do not have enough resources to trade away";
				}
				wood.setEnabled(false);
				bricks.setEnabled(false);
				wheat.setEnabled(false);
				sheep.setEnabled(false);
				ore.setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals("Sheep")) {
			if(message.equals("Click on a resource that you want to trade away")) {
				tradeAway = "Sheep";
				message = "Click on a resource that you want to trade for";
				sheep.setEnabled(false);
			}
			else {
				if(!(p1.trade(cb, tradeAway, "Sheep"))) {
					message = "You do not have enough resources to trade away";
				}
				wood.setEnabled(false);
				bricks.setEnabled(false);
				wheat.setEnabled(false);
				sheep.setEnabled(false);
				ore.setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals("Ore")) {
			if(message.equals("Click on a resource that you want to trade away")) {
				tradeAway = "Ore";
				message = "Click on a resource that you want to trade for";
				ore.setEnabled(false);
			}
			else {
				if(!(p1.trade(cb, tradeAway, "Ore"))) {
					message = "You do not have enough resources to trade away";
				}
				wood.setEnabled(false);
				bricks.setEnabled(false);
				wheat.setEnabled(false);
				sheep.setEnabled(false);
				ore.setEnabled(false);
			}
		}
		// i don't know why, but this keeps the buttons from disappearing
		endTurn.setBackground(Color.BLUE);
		buildRoad.setBackground(Color.BLUE);
		buildSettlement.setBackground(Color.BLUE);
		buildCity.setBackground(Color.BLUE);
		trade.setBackground(Color.BLUE);
		wood.setBackground(Color.BLUE);
		bricks.setBackground(Color.BLUE);
		wheat.setBackground(Color.BLUE);
		sheep.setBackground(Color.BLUE);
		ore.setBackground(Color.BLUE);
		for(int pb = 0; pb < pathButtons.length; pb++) {
			pathButtons[pb].setBackground(Color.BLUE);
		}
		for(int vb = 0; vb < vertexButtons.length; vb++) {
			vertexButtons[vb].setBackground(Color.BLUE);;
		}
		repaint();
	}
	
	//this 'rolls' 2 '6-sided dice' and gives players resources corresponding to the number rolled
	public int resourceRoll(CatanBoard cb, Player a, Player b, Player c, Player d) {
		Player[] abc = {a,b,c,d};
		int roll = cb.rollDice();
		//System.out.println("Number Rolled: " + roll);
		for (int k = 0; k < cb.getVertices().length; k++) {
			for (int j = 0; j < abc.length; j++) {
				if (cb.getHexagon(cb.getVertices(k).getHex1()).getValue() == roll) {
					if (abc[j].getColor().equals(cb.getVertices(k).getSettlement())) {
						abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex1()).getResource(), 1);
						if (cb.getVertices(k).getIsCity() == true) {
							abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex1()).getResource(), 1);
						}
					}
				}
				if (cb.getHexagon(cb.getVertices(k).getHex2()).getValue() == roll) {
					if (abc[j].getColor().equals(cb.getVertices(k).getSettlement())) {
						abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex2()).getResource(), 1);
						if (cb.getVertices(k).getIsCity() == true) {
							abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex2()).getResource(), 1);
						}
					}
				}
				if (cb.getHexagon(cb.getVertices(k).getHex3()).getValue() == roll) {
					if (abc[j].getColor().equals(cb.getVertices(k).getSettlement())) {
						abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex3()).getResource(), 1);
						if (cb.getVertices(k).getIsCity() == true) {
							abc[j].addResource(cb.getHexagon(cb.getVertices(k).getHex3()).getResource(), 1);
						}
					}
				}
			}
		}
		return roll;
	}
	
	public void checkLongestRoad() {
		if (p1.getRoadList().size() >= 5 && p2.getRoadList().size() >= 5 &&
				p3.getRoadList().size() >= 5 && p3.getRoadList().size() >= 5) {
			int road1 = p1.findLongRoad();
			int road2 = p2.findLongRoad();
			int road3 = p3.findLongRoad();
			int road4 = p4.findLongRoad();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 65) {
			if (p1.getVP() >= 10 || p2.getVP() >= 10 || p3.getVP() >= 10 || p4.getVP() >= 10) { return; }
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}


