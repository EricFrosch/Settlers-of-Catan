package catanGameModified;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player {
	
	private Resource Wood;
	private Resource Sheep;
	private Resource Ore;
	private Resource Brick;
	private Resource Wheat;
	protected Color color;
	private int victoryPoints;
	private ArrayList<Path> roadList;
	
	public Player(Color c) {
		Wood = new Resource("Wood", 0);
		Sheep = new Resource("Sheep", 0);
		Ore = new Resource("Ore", 0);
		Brick = new Resource("Brick", 0);
		Wheat = new Resource("Wheat", 0);
		color = c;
		victoryPoints = 0;
	}
	
	public ArrayList<Path> getRoadList() {return roadList;}
	
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
		// checks the vertex for a settlement
		if (v.getSettlement() != Color.white) {
			//System.out.println("You cannot build a settlement at " + v.getHex1() + v.getHex2() + v.getHex3());
			return false;	
		}
		// checks adjacent vertices for settlements
		for (int k = 0; k < b.getVertices().length; k++) {
			//this MASSIVE if boolean statement checks the 3 vertices adjacent to the given vertex, and checks to see if their are any settlements in those vertices
			if (((b.getVertices(k).getHex1() == v.getHex1()) || (b.getVertices(k).getHex1() == v.getHex2()) || (b.getVertices(k).getHex1() == v.getHex3())) && ((b.getVertices(k).getHex2() == v.getHex1()) || (b.getVertices(k).getHex2() == v.getHex2()) || (b.getVertices(k).getHex2() == v.getHex3())) ||
				((b.getVertices(k).getHex1() == v.getHex1()) || (b.getVertices(k).getHex1() == v.getHex2()) || (b.getVertices(k).getHex1() == v.getHex3())) && ((b.getVertices(k).getHex3() == v.getHex1()) || (b.getVertices(k).getHex3() == v.getHex2()) || (b.getVertices(k).getHex3() == v.getHex3())) ||
				((b.getVertices(k).getHex2() == v.getHex1()) || (b.getVertices(k).getHex2() == v.getHex2()) || (b.getVertices(k).getHex2() == v.getHex3())) && ((b.getVertices(k).getHex3() == v.getHex1()) || (b.getVertices(k).getHex3() == v.getHex2()) || (b.getVertices(k).getHex3() == v.getHex3()))) {
				if (b.getVertices(k).getSettlement() != Color.white) {
					//System.out.println("You cannot build a settlement at " + v.getHex1() + v.getHex2() + v.getHex3());
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
			//System.out.println("You cannot build a settlement");
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
			//System.out.println("You cannot build a city");
			return;
		}
		if ((v.getSettlement() != color) || (v.getIsCity() == true)) {
			//System.out.println("You cannot build a city at " + v.getHex1() + v.getHex2() + v.getHex3());
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
			//System.out.println("You cannot build a road");
			return;
		}
		if (p.getRoad() != null) {
			//System.out.println("You cannot build a road here");
			return;
		}
		p.drawRoad(g, color);
		p.setRoad(color);		
		this.subtractBrick(1); this.subtractWood(1);
		roadList.add(p);
		
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
	// returns true if successful and false if not
	public boolean trade(CatanBoard b, String x, String y) {
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
			//System.out.println("You do not have enough " + x + " to trade.");
			return false;
		}
		subtractResource(x, loss);
		addResource(y, gain);
		return true;
	}
	
	// in the lists in the road path list the first index is the road and all the others are the roads that connect to it
	private ArrayList<ArrayList<Path>> roadPathsList = new ArrayList<ArrayList<Path>>();
			
	// this is going to run findLongRoad for each starting point
	public int findLongestRoad() {
		// list of all the roads that are at the end of the entire road 
		ArrayList<Path> startPoints = new ArrayList<Path>();
		// the absolute longest road out of every path that was taken
		int longestRoad = 0;
		// the longest road from one path that is being checked at the moment 
		int longRoad;
		// empties roadPathsList so it can be rebuilt
		roadPathsList.clear();
		// fills the roadPathsList with lists and puts one road into each of those lists
		for(int r = 0; r < roadList.size(); r++) {
			roadPathsList.add(new ArrayList<Path>());
			roadPathsList.get(r).add(roadList.get(r));
		}
		// adds each road that connects to the road in the first index of each list in the roadPathsList
		for(int rp = 0; rp < roadPathsList.size(); rp++) {
			for(int r = 0; r < roadList.size(); r++) {
				// checks if the either vertex of the road matches with either vertex of every other road, but not both vertices
				if((roadPathsList.get(rp).get(0).getV1() == roadList.get(r).getV1() ||
					roadPathsList.get(rp).get(0).getV1() == roadList.get(r).getV2() ||
					roadPathsList.get(rp).get(0).getV2() == roadList.get(r).getV1() ||
					roadPathsList.get(rp).get(0).getV2() == roadList.get(r).getV2()) &&
					!((roadPathsList.get(rp).get(0).getV1() == roadList.get(r).getV1() ||
						roadPathsList.get(rp).get(0).getV1() == roadList.get(r).getV2()) &&
						(roadPathsList.get(rp).get(0).getV2() == roadList.get(r).getV1() ||
						roadPathsList.get(rp).get(0).getV2() == roadList.get(r).getV2()))) {
					roadPathsList.get(rp).add(roadList.get(r));
				}
			}
		}
		// finds the starting points
		for(int i = 0; i < roadPathsList.size(); i++) {
			// if a list in roadPathsList has only two values, then it is a road that connects to only one other road
			if(roadPathsList.get(i).size() == 2) {
				startPoints.add(roadPathsList.get(i).get(0));
			}
		}
		
		// finds the longest road from each starting point and compares them
		for(int s = 0; s < startPoints.size(); s++) {
			longRoad = findLongRoad(startPoints.get(s));
			if(longRoad > longestRoad) {
				longestRoad = longRoad;
			}
		}
		// if the longest road is less than 6 and it has 6 roads all bordering one hexagon, the the longest road is 6
		// this means I don't have to worry about a road with no end points.
		// there is still technically a problem of two or 3 adjacent hexagons but only an idiot would build roads like that so they don't deserve longest road
		if(longestRoad <= 5) {
			// each index is the letter of a hex starting from A and going to S
			ArrayList<Integer> numHexs = new ArrayList<Integer>();
			for(int i = 0; i < 19; i++) {
				numHexs.add(0);
			}
			// go through road list and find every hexagon of every vertex 
			// if a hexagon appears 12 times then there is a hexagon that is completely surrounded
			for( int r = 0; r < roadList.size(); r++) {
				numHexs.add(Character.getNumericValue(roadList.get(r).getV1().getHex1())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV1().getHex1())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV1().getHex1())-8);
				
				numHexs.add(Character.getNumericValue(roadList.get(r).getV1().getHex2())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV1().getHex2())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV1().getHex2())-8);
				
				numHexs.add(Character.getNumericValue(roadList.get(r).getV1().getHex3())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV1().getHex3())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV1().getHex3())-8);
				
				numHexs.add(Character.getNumericValue(roadList.get(r).getV2().getHex1())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV2().getHex1())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV2().getHex1())-8);
				
				numHexs.add(Character.getNumericValue(roadList.get(r).getV2().getHex2())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV2().getHex2())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV2().getHex2())-8);
				
				numHexs.add(Character.getNumericValue(roadList.get(r).getV2().getHex3())-9, 
						numHexs.get(Character.getNumericValue(roadList.get(r).getV2().getHex3())-9)+1);
				numHexs.remove(Character.getNumericValue(roadList.get(r).getV2().getHex3())-8);
			}
			for(int i = 0; i < numHexs.size(); i++) {
				if(numHexs.get(i) == 12) {
					longestRoad = 6;
				}
			}
		}
		return longestRoad;
	}
	
	// finds the length of this player's longest road from one starting point
	public int findLongRoad(Path startPoint) {
		// vertices that have already been looked at
		ArrayList<Vertex> usedVertices = new ArrayList<Vertex>();
		// roads that have already been looked at
		ArrayList<Path> usedRoads = new ArrayList<Path>();
		// list of the path not taken when it branches
		ArrayList<Path> branches = new ArrayList<Path>();
		// list of branch lengths
		ArrayList<Integer> branchLengths = new ArrayList<Integer>();
		// the array in roadPathsList that is currently being looked at
		ArrayList<Path> curRoadsList = new ArrayList<Path>();
		// the next path to be looked at
		Path nextPath;
		// the path that is currently being looked at
		Path curPath;
		// the vertex that is currently being looked at
		Vertex curVertex;
		// keeps looping and looking for another path until there aren't anymore paths left in that line of roads.
		boolean pathsLeft = true;
		// the length of the current road
		int roadLength = 0;
		// the length of the longest road
		int longRoadLength = 0;
		// for each road, it is going to find the longest road path thing
		for(int r = 0; r < roadPathsList.size(); r++) {
			// sets the current path and curent road list
			curPath = roadPathsList.get(r).get(0);
			curRoadsList = roadPathsList.get(r);
			// sets the current vertex 
			if(curPath.getV1() == roadPathsList.get(r).get(1).getV1() || 
					curPath.getV1() == roadPathsList.get(r).get(1).getV2()) {
				curVertex = curPath.getV1();
			}
			else {
				curVertex = curPath.getV2();
			}
			// sets the next path
			nextPath = curRoadsList.get(1);
			// loops until it gets to the end of the road
			while(pathsLeft) {
				// adds one to the length of the road, then finds the next road
				roadLength++;
				usedRoads.add(curPath);
				curPath = nextPath;
				usedVertices.add(curVertex);
				// looks through roadPathsList until the curPath is found and before nextPath is changed
				for(int rp = 0; rp < roadPathsList.size(); rp++) {
					if(roadPathsList.get(rp).get(0) == curPath && curPath == nextPath) {
						// find the nextPath, if there is no next path, change pathsLeft to false, if there are more than one possible nextPath, mark it as a branch
						// check if the connecting roads aren't in usedRoads
						// also check if the shared vertex isn't in used vertices
						
					}
				}
			}
		}
		return 1/0;
	}
	
	// finds the length of a branch, will have to be recursive in case there is a branch on a branch
	public int findBranchLength() {
		
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
