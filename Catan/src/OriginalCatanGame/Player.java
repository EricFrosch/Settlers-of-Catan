package CatanGame;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	private Resource Wood;
	private Resource Sheep;
	private Resource Ore;
	private Resource Brick;
	private Resource Wheat;
	protected Color color;
	private int victoryPoints;
	
	public Player(Color c) {
		Wood = new Resource("Wood", 0);
		Sheep = new Resource("Sheep", 0);
		Ore = new Resource("Ore", 0);
		Brick = new Resource("Brick", 0);
		Wheat = new Resource("Wheat", 0);
		color = c;
		victoryPoints = 0;
	}
	
	public Resource getWood() { return Wood; }
	
	public Resource getSheep() { return Sheep; }
	
	public Resource getOre() { return Ore; }
	
	public Resource getBrick() { return Brick; }
	
	public Resource getWheat() { return Wheat; }
	
	public Color getColor() { return color; }
	
	public int getVP() { return victoryPoints; }
	
	public void addWood(int w) { Wood.addAmount(w);; }
	
	public void addSheep(int s) { Sheep.addAmount(s);; }
	
	public void addOre(int o) { Ore.addAmount(o);; }
	
	public void addBrick(int b) { Brick.addAmount(b);; }
	
	public void addWheat(int w) { Wheat.addAmount(w);; }
	
	public void subtractWood(int w) { Wood.subtractAmount(w);; }
	
	public void subtractSheep(int s) { Sheep.subtractAmount(s);; }
	
	public void subtractOre(int o) { Ore.subtractAmount(o);; }
	
	public void subtractBrick(int b) { Brick.subtractAmount(b);; }
	
	public void subtractWheat(int w) { Wheat.subtractAmount(w);; }
	
	public Resource getResource(String s) {
		if (s.equals("Wood")) { return Wood; }
		if (s.equals("Wheat")) { return Wheat; }
		if (s.equals("Sheep")) { return Sheep; }
		if (s.equals("Ore")) { return Ore; }
		if (s.equals("Brick")) { return Brick; }
		else
			return null;
	}
	
	public void addResource(String s, int a) {
		if (s.equals("Wood")) { addWood(a); }
		if (s.equals("Wheat")) { addWheat(a); }
		if (s.equals("Sheep")) { addSheep(a); }
		if (s.equals("Ore")) { addOre(a); }
		if (s.equals("Brick")) { addBrick(a); }
	}
	
	public void subtractResource(String s, int a) {
			if (s.equals("Wood")) { subtractWood(a); }
			if (s.equals("Wheat")) { subtractWheat(a); }
			if (s.equals("Sheep")) { subtractSheep(a); }
			if (s.equals("Ore")) { subtractOre(a); }
			if (s.equals("Brick")) { subtractBrick(a); }
	}
	
	//this method checks to see if a settlement can be built at a specific vertex
	public boolean checkSettlementEligibility(CatanBoard b, Vertex v) {
		if (v.getSettlement() != Color.white) {
			System.out.println("You cannot build a settlement at " + v.getHex1() + v.getHex2() + v.getHex3());
			return false;	
		}
		for (int k = 0; k < b.getVertices().length; k++) {
			//this MASSIVE if boolean statement checks the 3 vertices adjacent to the given vertex, and checks to see if their are any settlements in those vertices
			if (((b.getVertices(k).getHex1() == v.getHex1()) || (b.getVertices(k).getHex1() == v.getHex2()) || (b.getVertices(k).getHex1() == v.getHex3())) && ((b.getVertices(k).getHex2() == v.getHex1()) || (b.getVertices(k).getHex2() == v.getHex2()) || (b.getVertices(k).getHex2() == v.getHex3())) ||
				((b.getVertices(k).getHex1() == v.getHex1()) || (b.getVertices(k).getHex1() == v.getHex2()) || (b.getVertices(k).getHex1() == v.getHex3())) && ((b.getVertices(k).getHex3() == v.getHex1()) || (b.getVertices(k).getHex3() == v.getHex2()) || (b.getVertices(k).getHex3() == v.getHex3())) ||
				((b.getVertices(k).getHex2() == v.getHex1()) || (b.getVertices(k).getHex2() == v.getHex2()) || (b.getVertices(k).getHex2() == v.getHex3())) && ((b.getVertices(k).getHex3() == v.getHex1()) || (b.getVertices(k).getHex3() == v.getHex2()) || (b.getVertices(k).getHex3() == v.getHex3()))) {
				if (b.getVertices(k).getSettlement() != Color.white) {
					System.out.println("You cannot build a settlement at " + v.getHex1() + v.getHex2() + v.getHex3());
					return false;
				}
			}
		}
		return true;
	}
	
	//this method builds a settlement at a given vertex (draws the settlement on the board and subtracts the resources from the player's resource pool)
	//as long as the player has enough resources and the vertex is eligible
	public void buildSettlement(Graphics g, CatanBoard b, Vertex v) {
		if (v == null) {
			return;
		}
		if ((Wood.getAmount() < 1) || (Brick.getAmount() < 1) || (Sheep.getAmount() < 1) || (Wheat.getAmount() < 1)) {
			System.out.println("You cannot build a settlement");
			return;
		}
		if (checkSettlementEligibility(b,v)) {
			v.setSettlement(color);
			this.subtractBrick(1); this.subtractWood(1); this.subtractSheep(1); this.subtractWheat(1);
			v.drawSettlement(g, color);
			victoryPoints++;
		}
	}
	
	//this method builds a city on a given settlement (draws the city on the board and subtracts the resources from the player's resource pool)
	//as long as the player has enough resources and the settlement is eligible
	public void buildCity(Graphics g, Vertex v) {
		if (v == null) { return; }
		if ((Ore.getAmount() < 3) || (Wheat.getAmount() < 2)) {
			System.out.println("You cannot build a city");
			return;
		}
		if ((v.getSettlement() != color) || (v.getIsCity() == true)) {
			System.out.println("You cannot build a city at " + v.getHex1() + v.getHex2() + v.getHex3());
			return;
		}
		else {
			this.subtractOre(3); this.subtractWheat(2);
			v.setCity(true);
			v.drawCity(g, color);
			victoryPoints++;
		}
	}
	
	//this method builds a road on a given path (draws the path on the board and subtracts the resources from the player's resource pool)
	//as long as the player has enough resources and the path is eligible
	public void buildRoad(Graphics g, Path p) {
		if (p == null) {
			return;
		}
		if ((this.Brick.getAmount() < 1) || (this.Wood.getAmount() < 1)) {
			System.out.println("You cannot build a road");
			return;
		}
		if (p.getRoad() != null) {
			System.out.println("You cannot build a road here");
			return;
		}
		p.drawRoad(g, color);
		p.setRoad(color);		
		this.subtractBrick(1); this.subtractWood(1);
	}
	
	//this buildSettlement method works a bit differently. When the game starts, a player gets to build 2 settlements and 2 roads for free
	//without expending any resources, and then gets starting resources according to the hexes that the settlements were built by. this builds the 2 settlements
	//and 2 roads for free, and then adds the resources to the player's resource pool
	public void buildFirstSettlement(Graphics g, CatanBoard b, Vertex v, Path p) {
		Wood.addAmount(2);Sheep.addAmount(1);Wheat.addAmount(1);Brick.addAmount(2);
		buildSettlement(g, b, v);
		buildRoad(g, p);
		for (int k = 0; k < b.getHexagons().length; k++) {
			if (v.getHex1() == b.getHexagons(k).getName()) {
				addResource(b.getHexagons(k).getResource(), 1);
			}
			else if (v.getHex2() == b.getHexagons(k).getName()) {
				addResource(b.getHexagons(k).getResource(), 1);
			}
			else if (v.getHex3() == b.getHexagons(k).getName()) {
				addResource(b.getHexagons(k).getResource(), 1);
			}
		}
	}
	
	//this method allows a player to trade resources either using maritime trade (4 of any resource for 1 of any other resource) or a harbor (3 of any resource or
	//2 of a specific resource for 1 of any other resource)
	public void trade(CatanBoard b, String x, String y) {
		int loss = 4;
		int gain = 1;
		for (int k = 0; k < b.getVertices().length; k++) {
			if (b.getVertices(k).getSettlement().equals(color)) {
				if (b.getVertices(k).getHarbor().getResource().equals(x) || b.getVertices(k).getHarbor().getResource().equals("Any")) {
					loss = b.getVertices(k).getHarbor().getExchangeRate();
				}
			}
		}
		if (getResource(x).getAmount() < loss ) {
			System.out.println("You do not have enough " + x + " to trade.");
			return;
		}
		subtractResource(x, loss);
		addResource(y, gain);
	}
	
	public void playerDraw(Graphics g, int x, int y) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 100, 150);
		g.setColor(color);
		g.drawString("Player" + color, x + 5, y + 5);
		g.drawString("Wood: " + Wood.getAmount(), x + 5, y + 20);
		g.drawString("Bricks: " + Brick.getAmount(), x + 5, y + 35);
		g.drawString("Wheat: " + Wheat.getAmount(), x + 5, y + 50);
		g.drawString("Sheep: " + Sheep.getAmount(), x + 5, y + 65);
		g.drawString("Ore: " + Ore.getAmount(), x + 5, y + 80);
		g.drawString("Victory Points: " + victoryPoints, x + 5, y + 95);
	}
	
}
