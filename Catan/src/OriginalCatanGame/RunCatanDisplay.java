package CatanGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RunCatanDisplay extends JPanel implements ActionListener, KeyListener{
	
	private JFrame frame;
	private Timer timer;
	private CatanBoard cb = new CatanBoard(500, 350);
	private int xyz;
	private CatanPlayerAI p1 = new CatanPlayerAI(Color.blue);
	private CatanPlayerAI p2 = new CatanPlayerAI(Color.red);
	private CatanPlayerAI p3 = new CatanPlayerAI(Color.orange);
	private CatanPlayerAI p4 = new CatanPlayerAI(Color.black);
	
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
	}
	
	public void playersBuild(Graphics g) {
		//builds each bot's first settlement
		if (xyz == 0) {
		p1.buildFirstSettlement(g, cb, p1.lookForBestVertex(cb), p1.lookForBestPathPath(cb, p1.lookForBestVertex(cb)));
		p2.buildFirstSettlement(g, cb, p2.lookForBestVertex(cb), p2.lookForBestPathPath(cb, p2.lookForBestVertex(cb)));
		p3.buildFirstSettlement(g, cb, p3.lookForBestVertex(cb), p3.lookForBestPathPath(cb, p3.lookForBestVertex(cb)));
		p4.buildFirstSettlement(g, cb, p4.lookForBestVertex(cb), p4.lookForBestPathPath(cb, p4.lookForBestVertex(cb)));
		p4.buildFirstSettlement(g, cb, p4.lookForBestVertex(cb), p4.lookForBestPathPath(cb, p4.lookForBestVertex(cb)));
		p3.buildFirstSettlement(g, cb, p3.lookForBestVertex(cb), p3.lookForBestPathPath(cb, p3.lookForBestVertex(cb)));
		p2.buildFirstSettlement(g, cb, p2.lookForBestVertex(cb), p2.lookForBestPathPath(cb, p2.lookForBestVertex(cb)));
		p1.buildFirstSettlement(g, cb, p1.lookForBestVertex(cb), p1.lookForBestPathPath(cb, p1.lookForBestVertex(cb)));
		}
		//then roll the dice and the next bot takes a turn
		int rolled = resourceRoll(cb,p1,p2,p3,p4);
		if (xyz % 4 == 0) { p1.takeTurn(g, cb); }
		if (xyz % 4 == 1) { p2.takeTurn(g, cb); }
		if (xyz % 4 == 2) { p3.takeTurn(g, cb); }
		if (xyz % 4 == 3) { p4.takeTurn(g, cb); }
		System.out.println(xyz);
		String wxy = "" + rolled;
		g.setColor(Color.black);
		g.drawString(wxy, 15 * (xyz / 60), 10 + (xyz * 10) - (60 * 10 * (xyz / 60)));
		//win conditions; a square of the winner's color is drawn if a player meets the win condition
		if (p1.getVP() >= 10) { g.setColor(p1.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p2.getVP() >= 10) { g.setColor(p2.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p3.getVP() >= 10) { g.setColor(p3.getColor()); g.fillRect(450, 300, 100, 100); }
		if (p4.getVP() >= 10) { g.setColor(p4.getColor()); g.fillRect(450, 300, 100, 100); }
		xyz++;
	}
	
	public void paint(Graphics g) {
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
		playersBuild(g);
		//draws each player's stats
		p1.playerDraw(g, 1000, 100);
		p2.playerDraw(g, 1000, 250);
		p3.playerDraw(g, 1000, 400);
		p4.playerDraw(g, 1000, 550);
	}
	
	public static void main(String[] args){
		new RunCatanDisplay();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (p1.getVP() >= 10 || p2.getVP() >= 10 || p3.getVP() >= 10 || p4.getVP() >= 10) { return; }
		repaint();
	}
	
	//this 'rolls' 2 '6-sided dice' and gives players resources corresponding to the number rolled
	public int resourceRoll(CatanBoard cb, Player a, Player b, Player c, Player d) {
		Player[] abc = {a,b,c,d};
		int roll = cb.rollDice();
		System.out.println("Number Rolled: " + roll);
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


