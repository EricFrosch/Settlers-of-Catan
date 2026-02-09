package CatanGame;

import java.awt.Color;
import java.awt.Graphics;

public class Harbor {
	
	private int exchangeRate;
	private String resource;
	
	public Harbor(int rate, String res) {
		exchangeRate = rate;
		resource = res;
	}
	
	public int getExchangeRate() {
		return exchangeRate;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void drawHarbor(Graphics g, int x1, int y1, int x2, int y2) {
		g.setColor(Color.black);
		g.drawLine(x1, y1, x2, y2);
		String s = resource + ", " + exchangeRate + ":1";
		g.drawString(s, x2 - 15, y2);
	}

}
