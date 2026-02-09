package CatanGame;

import java.awt.Color;
import java.awt.Graphics;

public class Vertex {

	private char c1;
	private char c2;
	private char c3;
	private Harbor harbor;
	private Color settlement;
	private boolean isCity;
	private int xCoord;
	private int yCoord;
	
	//settlements are built between 3 hexes, so it has 3 char attributes that correspond to hexes with that name
	//they can also hold a settlement of a player's color and a city
	public Vertex(char a, char b, char c, Harbor h, int x, int y) {
		c1 = a;
		c2 = b;
		c3 = c;
		harbor = h;
		settlement = Color.white;
		isCity = false;
		xCoord = x;
		yCoord = y;
	}
	
	public void setSettlement(Color c) { settlement = c; }
	
	public void setCity(boolean b) { isCity = b; }
	
	public char getHex1() { return c1; }
	
	public char getHex2() { return c2; }
	
	public char getHex3() { return c3; }
	
	public Color getSettlement() { return settlement; }
	
	public boolean getIsCity() { return isCity; }
	
	public int getxCoord() { return xCoord; }
	
	public int getyCoord() { return yCoord; }
	
	public Harbor getHarbor() { return harbor; }
	
	public String toString() { return "" + c1 + c2 + c3; }
	
	//draws a settlement at the vertex
	public void drawSettlement(Graphics g, Color c) {
		g.setColor(c);
		int xps[] = {xCoord - 7, xCoord, xCoord + 7, xCoord + 7, xCoord - 7};
		int yps[] = {yCoord - 7, yCoord - 14, yCoord - 7, yCoord + 7, yCoord + 7};
		g.fillPolygon(xps, yps, 5);
	}
	
	//draws a city at the vertex
	public void drawCity(Graphics g, Color c) {
		g.setColor(c);
		g.fillRect(xCoord - 14, yCoord - 4, 7, 11);
	}
	
}
