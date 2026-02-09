package catanGameModified;

import java.awt.Graphics;

public class Hexagon {
	
	private char name;
	private String resource;
	private int value;
	private int pips;
	private boolean hasRobber;
	private int xCoord;
	private int yCoord;
	private int sideLength;
	
	//'ghost' hexagon constructor for the fake outer ring of hexagons
	public Hexagon() {
		name = '-';
		resource = "None";
		value = 0;
		pips = 0;
		hasRobber = false;
		xCoord = 0;
		yCoord = 0;
		sideLength = 0;
	}
	
	//real hexagon constructor that builds the hexagon according to its name, resource, and side length
	public Hexagon(char n, String r, int xc, int yc, int side) {
		name  = n;
		resource = r;
		char[] nameArray = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'};
		int[] valArray = new int[] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11,0};
		int[] pipArray = new int[] {4,1,5,2,5,3,4,1,2,3,5,3,4,3,4,5,2,2,0};
		for (int x = 0; x < nameArray.length; x++) {
			if (name == nameArray[x]) {
			value = valArray[x];
			pips = pipArray[x];
			}
		}
		hasRobber = false;
		xCoord = xc;
		yCoord = yc;
		sideLength = side;
	}
	
	public char getName() { return name; }
	
	public void setName(char n) { name = n; }
	
	public String getResource() { return resource; }
	
	public void setResource(String r) { resource = r; }
	
	public int getValue() { return value; }
	
	public void setValue(int v) { value = v; }
	
	public int getPips() { return pips; }
	
	public void setPips(int p) { pips = p; }
	
	public boolean getHasRobber() { return hasRobber; }
	
	public void setHasRobber(boolean r) { hasRobber = r; }
	
	public int getX() { return xCoord; }
	
	public int getY() { return yCoord; }
	
	public int getSideLength() { return sideLength; }
	
	//draws a hexagon according to its side length and center
	public void drawHexagon(Graphics g) {
		int leg1 = (int) (Math.sqrt(3.0) * (sideLength / 2));
		int[] xvals = {xCoord - leg1, xCoord, xCoord + leg1, xCoord + leg1, xCoord, xCoord - leg1};
		int[] yvals = {yCoord - (sideLength / 2), yCoord - sideLength, yCoord - (sideLength / 2), yCoord + (sideLength / 2), yCoord + sideLength, yCoord + (sideLength / 2)};
		g.drawPolygon(xvals, yvals, 6);
		g.drawString(resource, xCoord - 15, yCoord + 30);
		g.drawOval(xCoord - 15, yCoord - 15, 30, 30);
		String v = "" + name + value;
		g.drawString(v, xCoord - 10/*3*/, yCoord + 2);
		for (int i = 1; i <= pips; i++) {
			g.fillOval(xCoord - 12 + (i * 4), yCoord + 3, 3, 3);
		}
	}
	
	//swaps 2 hexagon's value, pip#, and robber value
	public void swapStuff(Hexagon other) {
		int val = this.value;
		int pip = this.pips;
		boolean robber = this.hasRobber;
		this.value = other.value;
		this.pips = other.pips;
		this.hasRobber = other.hasRobber;
		other.value = val;
		other.pips = pip;
		other.hasRobber = robber;
	}

}
