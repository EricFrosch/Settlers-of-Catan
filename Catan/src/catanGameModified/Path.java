package catanGameModified;

import java.awt.Color;
import java.awt.Graphics;

public class Path {
	
	private Color road;
	private Vertex v1;
	private Vertex v2;
	
	//a path connects 2 vertices, and can have a road built on it. so it has 2 vertex attributes and a color attribute that can hold a road color
	public Path(Vertex a, Vertex b) {
		v1 = a;
		v2 = b;
		road = null;
	}
	
	public Vertex getV1() { return v1; }
	
	public Vertex getV2() { return v2; }
	
	public Color getRoad() { return road; }
	
	public void setRoad(Color c) { road = c; }
	
	//sets the color of the path to a player color, if a player builds a road there
	public void drawRoad(Graphics g, Color c) {
		g.setColor(c);
		if (v1.getxCoord() == v2.getxCoord()) {
			g.drawLine(v1.getxCoord() + 1, v1.getyCoord(), v2.getxCoord() + 1, v2.getyCoord());
			g.drawLine(v1.getxCoord(), v1.getyCoord(), v2.getxCoord(), v2.getyCoord());
			g.drawLine(v1.getxCoord() - 1, v1.getyCoord(), v2.getxCoord() - 1, v2.getyCoord());
		}
		else {
			g.drawLine(v1.getxCoord(), v1.getyCoord() + 1, v2.getxCoord(), v2.getyCoord() + 1);
			g.drawLine(v1.getxCoord(), v1.getyCoord(), v2.getxCoord(), v2.getyCoord());
			g.drawLine(v1.getxCoord(), v1.getyCoord() - 1, v2.getxCoord(), v2.getyCoord() - 1);
		}
	}

}
