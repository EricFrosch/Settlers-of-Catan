package catanGameModified;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayBoard extends JPanel{
	
	public static void main(String[] args){
		DisplayBoard win = new DisplayBoard();
		JFrame frame = new JFrame("Catan Board");
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.add(win);	
		frame.setVisible(true);
	}
	
	CatanBoard cb = new CatanBoard(500, 350);
	Player p = new Player(Color.blue);
	
	public void paint(Graphics g) {
		cb.drawGameBoard(g);
		for (int k = 0; k < cb.getVertices().length; k++) {
			p.buildSettlement(g, cb, cb.getVertices(k));
			p.buildRoad(g, cb.getPaths(k));
			//cb.getVertices(k).drawSettlement(g, Color.blue);
		}
		//cb.getVertices(12).drawCity(g, Color.blue);
		//for (int k = 0; k < cb.getPaths().length; k++) {
		//	cb.getPaths(k).drawRoad(g, Color.blue);
		//}
		//p.buildSettlement(g, cb, cb.getVertices(3));
		//p.buildSettlement(g, cb, cb.getVertices(5));
		//p.buildCity(g, cb.getVertices(30));
	}
	
}


