package catanGameModified;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

public class CatanPlayerHuman extends Player{
	
	private Scanner input;

	public CatanPlayerHuman(Color c) {
		super(c);
		input = new Scanner(System.in);
		addWood(2);
		addSheep(1);
		addBrick(2);
		addWheat(1);
		addOre(0);
	}
	
	public Vertex chooseVertex(CatanBoard b) {
		// makes sure you have enough to build a settlement or city
		// just for tests
		addWood(1);
		addSheep(1);
		addBrick(1);
		addWheat(2);
		addOre(3);
		String v = "";
		while(v.length() > 3 || v.length() == 0) {
			System.out.println("Enter what vertex you would like to choose:" );
			v = input.next();
		}
		// loops through each index of the vertices array
		for(int i = 0; i < b.getVertices().length; i++) {
			// stores the hex characters of the vertex in a Character class variable so they can be converted to a string
			Character hex1 = b.getVertices(i).getHex1();
			Character hex2 = b.getVertices(i).getHex2();
			Character hex3 = b.getVertices(i).getHex3();
			// checks if each hex character in the vertex is in the inputed string and returns the vertex if it is true
			if(v.contains(hex1.toString()) && v.contains(hex2.toString()) && v.contains(hex3.toString())) {
				return b.getVertices(i);
			}
		}
		return null;
	}
	
}