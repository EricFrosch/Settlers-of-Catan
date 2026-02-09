package CatanGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class CatanPlayerAI extends Player{

	public CatanPlayerAI(Color c) {
		super(c);
	}
	
	public Vertex lookForBestVertex(CatanBoard b) {													//This method's aim is to find the most valuable
		int highestPips = 0;																		//vertex on a board by looking at resource output
		Vertex bestVertex = null;
		for (int k = 0; k < b.getVertices().length; k++) {
			int totalPips = 0;
			totalPips += b.getHexagon(b.getVertices(k).getHex1()).getPips();						//}These 3 lines sum up the value of the 3
			totalPips += b.getHexagon(b.getVertices(k).getHex2()).getPips();						//}hexes that intersect at a given vertices
			totalPips += b.getHexagon(b.getVertices(k).getHex3()).getPips();						//}
			totalPips += (4 - b.getVertices(k).getHarbor().getExchangeRate());						//this line prioritizes vertices with better harbors
			if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(k))) {
				highestPips = totalPips;
				bestVertex = b.getVertices(k);
			}
		}
		return bestVertex;
	}
	
	public Vertex lookForMyBestVertex(CatanBoard b) {												//this method looks for the most valuable vertex on a board
		int highestPips = 0;																		//that a player controls (has a road leading to) in functionally
		Vertex bestVertex = null;																	//the same way as lookForBestVertex
		for (int k = 0; k < b.getVertices().length; k++) {
			int totalPips = 0;
			for (int j = 0; j < b.getPaths().length; j++) {
				if (b.getVertices(k).equals(b.getPaths(j).getV1()) || b.getVertices(k).equals(b.getPaths(j).getV2())) {				//these two lines check to see if a
					if (b.getPaths(j).getRoad() == super.color) {																	//player controls any given vertex
						totalPips += b.getHexagon(b.getVertices(k).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(k).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(k).getHex3()).getPips();
						if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(k))) {
							highestPips = totalPips;
							bestVertex = b.getVertices(k);
						}
					}
				}
			}
		}
		return bestVertex;
	}
	
	public Vertex lookForMyBestSettlement(CatanBoard b) {											//this method finds the settlement tht a player has built 
		int highestPips = 0;																		//with the most resource output
		Vertex bestVertex = lookForMyBestVertex(b);
		for (int k = 0; k < b.getVertices().length; k++) {
			int totalPips = 0;
			for (int j = 0; j < b.getPaths().length; j++) {
				if (b.getVertices(k).equals(b.getPaths(j).getV1()) || b.getVertices(k).equals(b.getPaths(j).getV2())) {
					if (b.getPaths(j).getRoad() == super.color) {
						totalPips += b.getHexagon(b.getVertices(k).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(k).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(k).getHex3()).getPips();
						if ((totalPips > highestPips) && (b.getVertices(k).getSettlement() == super.color) && !(b.getVertices(k).getIsCity())) {	//<this checks if the
							highestPips = totalPips;																								//settlement is the
							bestVertex = b.getVertices(k);																							//player's color and NOT
						}																															//already a city
					}
				}
			}
		}
		return bestVertex;
	}
	
	//NOT USED - DOESN'T QUITE WORK YET - IGNORE
	public Path lookForBestPathPathBetterVersion(CatanBoard b, Vertex v) {
		Path bestPath = null;
		int highestPips = 0;
		Vertex bestVertex = null;
		ArrayList<Path> vertexPaths = new ArrayList<Path>();
		for (int k = 0; k < vertexPaths.size(); k++) {
			if ((vertexPaths.get(k).getV1().equals(v))) {
				if (!(vertexPaths.get(k).getV2().equals(v))) {
					for (int j = 0; j < b.getVertices().length; j++) {
						int totalPips = 0;
						totalPips += b.getHexagon(b.getVertices(j).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex3()).getPips();
						if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(j))) {
							highestPips = totalPips;
							bestVertex = b.getVertices(j);
						}
					}
				}
			}
			else {
				if (!(vertexPaths.get(k).getV1().equals(v))) {
					for (int j = 0; j < b.getVertices().length; j++) {
						int totalPips = 0;
						totalPips += b.getHexagon(b.getVertices(j).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex3()).getPips();
						if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(j))) {
							highestPips = totalPips;
							bestVertex = b.getVertices(j);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < vertexPaths.size(); i++) {
			if (vertexPaths.get(i).getV1().equals(bestVertex) || vertexPaths.get(i).getV2().equals(bestVertex)) {
				bestPath = vertexPaths.get(i);
			}
		}
		return bestPath;
	}
	
	//NOT USED - DOESN'T QUITE WORK YET - IGNORE
	public int lookForBestPathPipsBetterVersion(CatanBoard b, Vertex v) {
		Path bestPath = null;
		int highestPips = 0;
		Vertex bestVertex = null;
		ArrayList<Path> vertexPaths = new ArrayList<Path>();
		for (int k = 0; k < vertexPaths.size(); k++) {
			if ((vertexPaths.get(k).getV1().equals(v))) {
				if (!(vertexPaths.get(k).getV2().equals(v))) {
					for (int j = 0; j < b.getVertices().length; j++) {
						int totalPips = 0;
						totalPips += b.getHexagon(b.getVertices(j).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex3()).getPips();
						if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(j))) {
							highestPips = totalPips;
							bestVertex = b.getVertices(j);
						}
					}
				}
			}
			else {
				if (!(vertexPaths.get(k).getV1().equals(v))) {
					for (int j = 0; j < b.getVertices().length; j++) {
						int totalPips = 0;
						totalPips += b.getHexagon(b.getVertices(j).getHex1()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex2()).getPips();
						totalPips += b.getHexagon(b.getVertices(j).getHex3()).getPips();
						if ((totalPips > highestPips) && checkSettlementEligibility(b, b.getVertices(j))) {
							highestPips = totalPips;
							bestVertex = b.getVertices(j);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < vertexPaths.size(); i++) {
			if (vertexPaths.get(i).getV1().equals(bestVertex) || vertexPaths.get(i).getV2().equals(bestVertex)) {
				bestPath = vertexPaths.get(i);
			}
		}
		return highestPips;
	}
	
	public Path lookForBestPathPath(CatanBoard b, Vertex v) {										//this method looks for the best path that connects to a specific vertex
		Path bestPath = null;																		//it's kind of really complicated and it doesn't make a whole lot of
		int highestPips = 0;																		//sense unless you know all of the game rules so i'm just gonna leave it
		//Vertex bestVertex = null;																	//at that
		ArrayList<Path> vertexPaths = new ArrayList<Path>();
		for (int k = 0; k < b.getPaths().length; k++) {
			if (v.equals(b.getPaths(k).getV1()) || v.equals(b.getPaths(k).getV2())) {
				if (b.getPaths(k).getRoad() != super.color) {
					vertexPaths.add(b.getPaths(k));
				}
			}
		}
		
		for (int k = 0; k < vertexPaths.size(); k++) {
			if ((vertexPaths.get(k).getV1().equals(v))) {
				if (!(vertexPaths.get(k).getV2().equals(v))) {
					char h1 = vertexPaths.get(k).getV2().getHex1();
					char h2 = vertexPaths.get(k).getV2().getHex2();
					char h3 = vertexPaths.get(k).getV2().getHex3();
					if ((h1 != v.getHex1()) && (h1 != v.getHex2()) && (h1 != v.getHex3())) {
						if ((b.getHexagon(h1).getPips() + 4 - vertexPaths.get(k).getV2().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h1).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h2 != v.getHex1()) && (h2 != v.getHex2()) && (h2 != v.getHex3())) {
						if ((b.getHexagon(h2).getPips()  + 4 - vertexPaths.get(k).getV2().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h3 != v.getHex1()) && (h3 != v.getHex2()) && (h3 != v.getHex3())) {
						if ((b.getHexagon(h2).getPips()  + 4 - vertexPaths.get(k).getV2().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
				}
			}
			else 
				if (!(vertexPaths.get(k).getV1().equals(v))) {
					char h1 = vertexPaths.get(k).getV1().getHex1();
					char h2 = vertexPaths.get(k).getV1().getHex2();
					char h3 = vertexPaths.get(k).getV1().getHex3();
					if ((h1 != v.getHex1()) && (h1 != v.getHex2()) && (h1 != v.getHex3())) {
						if ((b.getHexagon(h1).getPips()  + 4 - vertexPaths.get(k).getV1().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h1).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h2 != v.getHex1()) && (h2 != v.getHex2()) && (h2 != v.getHex3())) {
						if ((b.getHexagon(h2).getPips() + 4 - vertexPaths.get(k).getV1().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h3 != v.getHex1()) && (h3 != v.getHex2()) && (h3 != v.getHex3())) {
						if ((b.getHexagon(h2).getPips() + 4 - vertexPaths.get(k).getV1().getHarbor().getExchangeRate()) > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
				}	
			}
		return bestPath;
	}
	
	public int lookForBestPathPips(CatanBoard b, Vertex v) {										//this method does the same thing as the last method but it returns the 
		Path bestPath = null;																		//number of pips that the hexagon that the best path leads to, rather
		int highestPips = 0;																		//than the path itself. I would only do one method except I can't return
		//Vertex bestVertex = null;																	//two values from one function and Path objects don't have a pip value,
		ArrayList<Path> vertexPaths = new ArrayList<Path>();										//nor can they because paths lead to 2 different hexes and the 'pip
		for (int k = 0; k < b.getPaths().length; k++) {												//value' of a path is relative to the direction I'm looking at it from.
			if (v.equals(b.getPaths(k).getV1()) || v.equals(b.getPaths(k).getV2())) {
				if (b.getPaths(k).getRoad() != super.color) {
					vertexPaths.add(b.getPaths(k));
				}
			}
		}
		
		for (int k = 0; k < vertexPaths.size(); k++) {
			if ((vertexPaths.get(k).getV1().equals(v))) {
				if (!(vertexPaths.get(k).getV2().equals(v))) {
					char h1 = vertexPaths.get(k).getV2().getHex1();
					char h2 = vertexPaths.get(k).getV2().getHex2();
					char h3 = vertexPaths.get(k).getV2().getHex3();
					if ((h1 != v.getHex1()) && (h1 != v.getHex2()) && (h1 != v.getHex3())) {
						if (b.getHexagon(h1).getPips() > highestPips) {
							highestPips = b.getHexagon(h1).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h2 != v.getHex1()) && (h2 != v.getHex2()) && (h2 != v.getHex3())) {
						if (b.getHexagon(h2).getPips() > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h3 != v.getHex1()) && (h3 != v.getHex2()) && (h3 != v.getHex3())) {
						if (b.getHexagon(h2).getPips() > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
				}
			}
			else 
				if (!(vertexPaths.get(k).getV1().equals(v))) {
					char h1 = vertexPaths.get(k).getV1().getHex1();
					char h2 = vertexPaths.get(k).getV1().getHex2();
					char h3 = vertexPaths.get(k).getV1().getHex3();
					if ((h1 != v.getHex1()) && (h1 != v.getHex2()) && (h1 != v.getHex3())) {
						if (b.getHexagon(h1).getPips() > highestPips) {
							highestPips = b.getHexagon(h1).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h2 != v.getHex1()) && (h2 != v.getHex2()) && (h2 != v.getHex3())) {
						if (b.getHexagon(h2).getPips() > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
					if ((h3 != v.getHex1()) && (h3 != v.getHex2()) && (h3 != v.getHex3())) {
						if (b.getHexagon(h2).getPips() > highestPips) {
							highestPips = b.getHexagon(h2).getPips();
							bestPath = vertexPaths.get(k);
						}
					}
				}	
			}
		return highestPips;
	}
	
	public Path lookForBestPath(CatanBoard b) {													//This method looks for the best path that a player could build a road on
		ArrayList<Vertex> myVertices = new ArrayList<Vertex>();									//relative to a system of roads
		ArrayList<Path> myPaths = new ArrayList<Path>();										
		ArrayList<Integer> pathPips = new ArrayList<Integer>();
		for (int k = 0; k < b.getPaths().length; k++) {											//this loop adds all of the vertices that are connected by a player's roads
			if (b.getPaths(k).getRoad() == super.color) {										//to one arrayList
				myVertices.add(b.getPaths(k).getV1());
				myVertices.add(b.getPaths(k).getV2());
			}
		}
		for (int k = 0; k < myVertices.size(); k++) {											//this loop looks at every vertex in the previously created arrayList and
			myPaths.add(lookForBestPathPath(b, myVertices.get(k)));								//finds the best path leading away from that vertex
			pathPips.add(lookForBestPathPips(b, myVertices.get(k)));		
			//myPaths.add(lookForBestPathPathBetterVersion(b, myVertices.get(k)));
			//pathPips.add(lookForBestPathPipsBetterVersion(b, myVertices.get(k)));
		}
		int highestPips = 0;
		int highestIndex = 0;
		for (int k = 0; k < pathPips.size(); k++) {												//this loop goes through the arraylists of the best path for each vertex,
			if ((pathPips.get(k) > highestPips) && (myPaths.get(k).getRoad() == null)) {		//and finds the best path out of those paths
				highestPips = pathPips.get(k);
				highestIndex = k;
			}
		}
		return myPaths.get(highestIndex);
	}

	public void takeTurn(Graphics g, CatanBoard b) {											//this method allows for the bot to actually take a turn
		//the following string of 9 if statements check to see if the bot is in need of any resources, and if it is,
		//trade for that needed resource using the resource that has the most excess of.
		String needResource = "Ore";
		if (super.getBrick().getAmount() > super.getResource(needResource).getAmount()) { needResource = super.getBrick().getName(); }
		if (super.getSheep().getAmount() > super.getResource(needResource).getAmount()) { needResource = super.getSheep().getName(); }
		if (super.getWood().getAmount() > super.getResource(needResource).getAmount()) { needResource = super.getWood().getName(); }
		if (super.getWheat().getAmount() > super.getResource(needResource).getAmount()) { needResource = super.getWheat().getName(); }
		if (super.getWood().getAmount() <= 1) { super.trade(b, needResource, "Wood"); }
		if (super.getBrick().getAmount() <= 1) { super.trade(b, needResource, "Brick"); }
		if (super.getSheep().getAmount() == 0) { super.trade(b, needResource, "Sheep"); }
		if (super.getWheat().getAmount() <= 1) { super.trade(b, needResource, "Wheat"); }
		if (super.getOre().getAmount() <= 2) { super.trade(b, needResource, "Ore"); }
		//the following if statement checks to see if the bot has enough resources to build a settlement, and builds one if it does
		if ((super.getWood().getAmount() >= 1) && (super.getBrick().getAmount() >= 1) && (super.getSheep().getAmount() >= 1) && (super.getWheat().getAmount() >= 1)) {
			super.buildSettlement(g, b, lookForMyBestVertex(b));
		}
		//the following if statement checks to see if the bot has enough resources to build a roas, and builds one if it does
		//(it also always keeps 1 wood and 1 brick in reserve to be used for building settlements)
		if ((super.getWood().getAmount() >= 2) && (super.getBrick().getAmount() >= 2)) {
			super.buildRoad(g, lookForBestPath(b));
		}
		//the following if statement checks to see if the bot has enough resources to upgrade a settlement to a city, and builds a city if it does
		if ((super.getOre().getAmount() >= 3) && (super.getWheat().getAmount() >= 2)) {
			super.buildCity(g, lookForMyBestSettlement(b));
		}
	}
	
}
