package catanGameModified;

import java.awt.Graphics;
import java.util.ArrayList;

public class CatanBoard {
	
	 private Path[] boardPaths;
	 private Vertex[] boardVertices;
	 private Hexagon[] boardHexagons;
	 private int centerX;
	 private int centerY;
	 
	 public CatanBoard(int cX, int cY) {
		centerX = cX;
		centerY = cY;
		makeHexagons(74);
		makeVertices(74);
		makePaths();		 
	 }
	 
	 private void makeVertices(int apothem) {
		//Harbors
		 Harbor r4 = new Harbor(4, "Any");
		 Harbor r3 = new Harbor(3, "Any");
		 Harbor b2 = new Harbor(2, "Brick");
		 Harbor wo2 = new Harbor(2, "Wood");
		 Harbor we2 = new Harbor(2, "Wheat");
		 Harbor o2 = new Harbor(2, "Ore");
		 Harbor s2 = new Harbor(2, "Sheep");
		 
		 int quarter = apothem / 2;
		 int half = (int) (Math.sqrt(3.0) * quarter);
		 int xp[] = new int[11];
		 int yp[] = new int[17];
		 for (int i = 0; i < xp.length; i++) {
			 if (i < 5) { xp[i] = centerX - ((5 - i) * half); }
			 else if (i > 5) { xp[i] = centerX + ((i - 5) * half); }
			 else { xp[i] = centerX; }
		 }
		 for (int i = 0; i < yp.length; i++) {
			 if (i < 8) { yp[i] = centerY - ((8 - i) * quarter); }
			 else if (i > 8) { yp[i] = centerY + ((i - 8) * quarter); }
			 else { yp[i] = centerY; }
		 }
		 
		 //'coast'/outer ring vertices
		 // Eric: the name of each vertex is the hexagons that are adjacent to it
		 // lower case letters mean that it isn't adjacent, I don't know if they mean anything else yet
		 Vertex Alm = new Vertex('A','l','m',r3,xp[0],yp[9]); Vertex Abm = new Vertex('A','a','m',r3,xp[0],yp[7]); Vertex ABa = new Vertex('A','B','a',r4,xp[1],yp[6]);
		 Vertex Bx0 = new Vertex('B','a','b',b2,xp[1],yp[4]); Vertex BCb = new Vertex('B','C','b',b2,xp[2],yp[3]); Vertex Cbn = new Vertex('C','b','n',r4,xp[2],yp[1]);
		 Vertex Ccn = new Vertex('C','c','n',r4,xp[3],yp[0]); Vertex CDc = new Vertex('C','D','c',wo2,xp[4],yp[1]); Vertex Dcd = new Vertex('D','c','d',wo2,xp[5],yp[0]);
		 Vertex DEd = new Vertex('D','E','d',r4,xp[6],yp[1]); Vertex Edo = new Vertex('E','d','o',r3,xp[7],yp[0]); Vertex Eeo = new Vertex('E','e','o',r3,xp[8],yp[1]);
		 Vertex EFe = new Vertex('E','F','e',r4,xp[8],yp[3]); Vertex Fef = new Vertex('F','e','f',we2,xp[9],yp[4]); Vertex FGf = new Vertex('F','G','f',we2,xp[9],yp[6]);
		 Vertex Gfp = new Vertex('G','f','p',r4,xp[10],yp[7]); Vertex Ggp = new Vertex('G','g','p',r4,xp[10],yp[9]); Vertex GHg = new Vertex('G','H','g',o2,xp[9],yp[10]);
		 Vertex Hgh = new Vertex('H','g','h',o2,xp[9],yp[12]); Vertex HIh = new Vertex('H','I','h',r4,xp[8],yp[13]); Vertex Ihq = new Vertex('I','h','q',r3,xp[8],yp[15]);
		 Vertex Iiq = new Vertex('I','i','q',r3,xp[7],yp[16]); Vertex IJi = new Vertex('I','J','i',r4,xp[6],yp[15]); Vertex Jij = new Vertex('J','i','j',s2,xp[5],yp[16]);
		 Vertex JKj = new Vertex('J','K','j',s2,xp[4],yp[15]); Vertex Kjr = new Vertex('K','j','r',r4,xp[3],yp[16]); Vertex Kkr = new Vertex('K','k','r',r4,xp[2],yp[15]);
		 Vertex KLk = new Vertex('K','L','k',r3,xp[2],yp[13]); Vertex Lkl = new Vertex('L','k','l',r3,xp[1],yp[12]); Vertex ALl = new Vertex('A','L','l',r4,xp[1],yp[10]);
		 //middle ring vertices
		 Vertex ABM = new Vertex('A','B','M',r4,xp[2],yp[7]); Vertex BMN = new Vertex('B','M','N',r4,xp[3],yp[6]); Vertex BCN = new Vertex('B','C','N',r4,xp[3],yp[4]);
		 Vertex CDN = new Vertex('C','D','N',r4,xp[4],yp[3]); Vertex DNO = new Vertex('D','N','O',r4,xp[5],yp[4]); Vertex DEO = new Vertex('D','E','O',r4,xp[6],yp[3]);
		 Vertex EFO = new Vertex('E','F','O',r4,xp[7],yp[4]); Vertex FOP = new Vertex('F','O','P',r4,xp[7],yp[6]); Vertex FGP = new Vertex('F','G','P',r4,xp[8],yp[7]);
		 Vertex GHP = new Vertex('G','H','P',r4,xp[8],yp[9]); Vertex HPQ = new Vertex('H','P','Q',r4,xp[7],yp[10]); Vertex HIQ = new Vertex('H','I','Q',r4,xp[7],yp[12]);
		 Vertex IJQ = new Vertex('I','J','Q',r4,xp[6],yp[13]); Vertex JQR = new Vertex('J','Q','R',r4,xp[5],yp[12]); Vertex JKR = new Vertex('J','K','R',r4,xp[4],yp[13]);
		 Vertex KLR = new Vertex('K','L','R',r4,xp[3],yp[12]); Vertex LRM = new Vertex('L','R','M',r4,xp[3],yp[10]); Vertex ALM = new Vertex('A','L','M',r4,xp[2],yp[9]);
		 //'desert'/inner ring vertices
		 Vertex MNS = new Vertex('M','N','S',r4,xp[4],yp[7]); Vertex NOS = new Vertex('N','O','S',r4,xp[5],yp[6]); Vertex OPS = new Vertex('O','P','S',r4,xp[6],yp[7]); 
		 Vertex PQS = new Vertex('P','Q','S',r4,xp[6],yp[9]); Vertex QRS = new Vertex('Q','R','S',r4,xp[5],yp[10]); Vertex MRS = new Vertex('M','R','S',r4,xp[4],yp[9]);
		 boardVertices = new Vertex[] {Alm,Abm,ABa,Bx0,BCb,Cbn,Ccn,CDc,Dcd,DEd,Edo,Eeo,EFe,Fef,FGf,Gfp,Ggp,GHg,Hgh,HIh,Ihq,Iiq,IJi,Jij,JKj,Kjr,Kkr,KLk,Lkl,ALl,ABM,BMN,BCN,CDN,DNO,DEO,EFO,FOP,FGP,GHP,HPQ,HIQ,IJQ,JQR,JKR,KLR,LRM,ALM,MNS,NOS,OPS,PQS,QRS,MRS};
	 }
	 
	 public void makeHexagons(int hexSide) {
		//resource hexes
		 ArrayList<String> resourceHexes = new ArrayList<String>();
		 resourceHexes.add("Ore"); resourceHexes.add("Ore"); resourceHexes.add("Ore");
		 resourceHexes.add("Brick"); resourceHexes.add("Brick"); resourceHexes.add("Brick");
		 resourceHexes.add("Wood"); resourceHexes.add("Wood"); resourceHexes.add("Wood"); resourceHexes.add("Wood"); 
		 resourceHexes.add("Sheep"); resourceHexes.add("Sheep"); resourceHexes.add("Sheep"); resourceHexes.add("Sheep"); 
		 resourceHexes.add("Wheat"); resourceHexes.add("Wheat"); resourceHexes.add("Wheat"); resourceHexes.add("Wheat"); 
		 resourceHexes.add("Desert");
		 //Hexagons
		 int radius = (int) (Math.sqrt(3.0) * (hexSide / 2));
		 int[] xp = {centerX - (radius * 4), centerX - (radius * 3), centerX - (radius * 2), centerX - radius, centerX, centerX + radius, centerX + (radius * 2), centerX + (radius * 3), centerX + (radius * 4),};
		 int[] yp = {centerY + (hexSide * 3), centerY + (int) (hexSide * 1.5), centerY, centerY - (int) (hexSide * 1.5), centerY - (hexSide * 3)};
		 Hexagon A = new Hexagon('A', null, xp[0], yp[2], hexSide); Hexagon B = new Hexagon('B', null, xp[1], yp[3], hexSide); 
		 Hexagon C = new Hexagon('C', null, xp[2], yp[4], hexSide); Hexagon D = new Hexagon('D', null, xp[4], yp[4], hexSide); 
		 Hexagon E = new Hexagon('E', null, xp[6], yp[4], hexSide); Hexagon F = new Hexagon('F', null, xp[7], yp[3], hexSide); 
		 Hexagon G = new Hexagon('G', null, xp[8], yp[2], hexSide); Hexagon H = new Hexagon('H', null, xp[7], yp[1], hexSide); 
		 Hexagon I = new Hexagon('I', null, xp[6], yp[0], hexSide); Hexagon J = new Hexagon('J', null, xp[4], yp[0], hexSide); 
		 Hexagon K = new Hexagon('K', null, xp[2], yp[0], hexSide); Hexagon L = new Hexagon('L', null, xp[1], yp[1], hexSide); 
		 Hexagon M = new Hexagon('M', null, xp[2], yp[2], hexSide); Hexagon N = new Hexagon('N', null, xp[3], yp[3], hexSide); 
		 Hexagon O = new Hexagon('O', null, xp[5], yp[3], hexSide); Hexagon P = new Hexagon('P', null, xp[6], yp[2], hexSide); 
		 Hexagon Q = new Hexagon('Q', null, xp[5], yp[1], hexSide); Hexagon R = new Hexagon('R', null, xp[3], yp[1], hexSide); 
		 Hexagon S = new Hexagon('S', null, xp[4], yp[2], hexSide); 
		 Hexagon[] boardHexes = new Hexagon[] {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S};
		 //putting them together
		 for (int c = 0; c < boardHexes.length; c++) {
			 int rando = (int) (Math.random() * resourceHexes.size());
			 boardHexes[c].setResource(resourceHexes.get(rando));
			 resourceHexes.remove(rando);
		 }
		 boardHexagons = new Hexagon[] {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S};
		 for (int j = 0; j < boardHexagons.length; j++) {
			 if (boardHexagons[j].getResource().equals("Desert")) {
				 for (int i = 0; i < boardHexagons.length; i++) {
					 if (boardHexagons[i].getName() == 'S') {
					 	boardHexagons[i].swapStuff(boardHexagons[j]);
					 }
				 }
			 }
		 }
	 }
	 
	 private void makePaths() {
		 //Outer Ring Paths
		 Path A0 = new Path(boardVertices[0],boardVertices[1]); Path Ab = new Path(boardVertices[1],boardVertices[2]); Path Ba = new Path(boardVertices[2],boardVertices[3]);
		 Path Bc = new Path(boardVertices[3],boardVertices[4]); Path Cb = new Path(boardVertices[4],boardVertices[5]); Path C0 = new Path(boardVertices[5],boardVertices[6]);
		 Path Cd = new Path(boardVertices[6],boardVertices[7]); Path Dc = new Path(boardVertices[7],boardVertices[8]); Path De = new Path(boardVertices[8],boardVertices[9]);
		 Path Ed = new Path(boardVertices[9],boardVertices[10]); Path E0 = new Path(boardVertices[10],boardVertices[11]); Path Ef = new Path(boardVertices[11],boardVertices[12]);
		 Path Fe = new Path(boardVertices[12],boardVertices[13]); Path Fg = new Path(boardVertices[13],boardVertices[14]); Path Gf = new Path(boardVertices[14],boardVertices[15]);
		 Path G0 = new Path(boardVertices[15],boardVertices[16]); Path Gh = new Path(boardVertices[16],boardVertices[17]); Path Hg = new Path(boardVertices[17],boardVertices[18]);
		 Path Hi = new Path(boardVertices[18],boardVertices[19]); Path Ih = new Path(boardVertices[19],boardVertices[20]); Path I0 = new Path(boardVertices[20],boardVertices[21]);
		 Path Ij = new Path(boardVertices[21],boardVertices[22]); Path Ji = new Path(boardVertices[22],boardVertices[23]); Path Jk = new Path(boardVertices[23],boardVertices[24]);
		 Path Kj = new Path(boardVertices[24],boardVertices[25]); Path K0 = new Path(boardVertices[25],boardVertices[26]); Path Kl = new Path(boardVertices[26],boardVertices[27]);
		 Path Lk = new Path(boardVertices[27],boardVertices[28]); Path La = new Path(boardVertices[28],boardVertices[29]); Path Al = new Path(boardVertices[29],boardVertices[0]);
		 //1st Transition Paths
		 Path AB = new Path(boardVertices[2],boardVertices[30]); Path BC = new Path(boardVertices[4],boardVertices[32]); Path CD = new Path(boardVertices[7],boardVertices[33]);
		 Path DE = new Path(boardVertices[9],boardVertices[35]); Path EF = new Path(boardVertices[12],boardVertices[36]); Path FG = new Path(boardVertices[14],boardVertices[38]);
		 Path GH = new Path(boardVertices[17],boardVertices[39]); Path HI = new Path(boardVertices[19],boardVertices[41]); Path IJ = new Path(boardVertices[22],boardVertices[42]);
		 Path JK = new Path(boardVertices[24],boardVertices[44]); Path KL = new Path(boardVertices[27],boardVertices[45]); Path AL = new Path(boardVertices[29],boardVertices[47]);
		 //Middle Ring Paths
		 Path AM = new Path(boardVertices[30],boardVertices[47]); Path BM = new Path(boardVertices[30],boardVertices[31]); Path BN = new Path(boardVertices[31],boardVertices[32]);
		 Path CN = new Path(boardVertices[32],boardVertices[33]); Path DN = new Path(boardVertices[33],boardVertices[34]); Path DO = new Path(boardVertices[34],boardVertices[35]);
		 Path EO = new Path(boardVertices[35],boardVertices[36]); Path FO = new Path(boardVertices[36],boardVertices[37]); Path FP = new Path(boardVertices[37],boardVertices[38]);
		 Path GP = new Path(boardVertices[38],boardVertices[39]); Path HP = new Path(boardVertices[39],boardVertices[40]); Path HQ = new Path(boardVertices[40],boardVertices[41]);
		 Path IQ = new Path(boardVertices[41],boardVertices[42]); Path JQ = new Path(boardVertices[42],boardVertices[43]); Path JR = new Path(boardVertices[43],boardVertices[44]);
		 Path KR = new Path(boardVertices[44],boardVertices[45]); Path LR = new Path(boardVertices[45],boardVertices[46]); Path LM = new Path(boardVertices[46],boardVertices[47]);
		 //2nd Transition Paths
		 Path MN = new Path(boardVertices[31],boardVertices[48]); Path NO = new Path(boardVertices[34],boardVertices[49]); Path OP = new Path(boardVertices[37],boardVertices[50]);
		 Path PQ = new Path(boardVertices[40],boardVertices[51]); Path QR = new Path(boardVertices[43],boardVertices[52]); Path RM = new Path(boardVertices[46],boardVertices[53]);
		 //Inner Ring Paths
		 Path MS = new Path(boardVertices[48],boardVertices[53]); Path NS = new Path(boardVertices[48],boardVertices[49]); Path OS = new Path(boardVertices[49],boardVertices[50]);
		 Path PS = new Path(boardVertices[50],boardVertices[51]); Path QS = new Path(boardVertices[51],boardVertices[52]); Path RS = new Path(boardVertices[52],boardVertices[53]);
		 boardPaths = new Path[] {A0,Ab,Ba,Bc,Cb,C0,Cd,Dc,De,Ed,E0,Ef,Fe,Fg,Gf,G0,Gh,Hg,Hi,Ih,I0,Ij,Ji,Jk,Kj,K0,Kl,Lk,La,Al,AB,BC,CD,DE,EF,FG,GH,HI,IJ,JK,KL,AL,AM,BM,BN,CN,DN,DO,EO,FO,FP,GP,HP,HQ,IQ,JQ,JR,KR,LR,LM,MN,NO,OP,PQ,QR,RM,MS,NS,OS,PS,QS,RS};
	 }
	 
	 public void drawGameBoard(Graphics g) {
		 for (int k = 0; k < boardHexagons.length; k++) {
			 boardHexagons[k].drawHexagon(g);
		 }
		 for (int k = 0; k < boardVertices.length; k++) {
			 if (boardVertices[k].getHarbor().getExchangeRate() != 4) {
				 int bvx = boardVertices[k].getxCoord();
				 int bvy = boardVertices[k].getyCoord();
				 if ((bvx < centerX) && (bvy < centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx - 40, bvy - 40);
				 }
				 else if ((bvx > centerX) && (bvy < centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx + 40, bvy - 40);
				 }
				 else if ((bvx < centerX) && (bvy > centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx - 40, bvy + 40);
				 }
				 else if ((bvx > centerX) && (bvy > centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx + 40, bvy + 40);
				 }
				 else if ((bvx == centerX) && (bvy > centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx, bvy + 40);
				 }
				 else if ((bvx == centerX) && (bvy < centerY)) {
					 boardVertices[k].getHarbor().drawHarbor(g, bvx, bvy, bvx, bvy - 40);
				 }
			 }
		 }
	 }
	 
	 //rolls 2 6 sided 'dice' and returns their added value (that's how you start a turn)
	 public int rollDice() {
		 int die1 = (int) (Math.random() * 6) + 1;
		 int die2 = (int) (Math.random() * 6) + 1;
		 return die1 + die2;
	 }
	 
	 public Vertex getVertices(int i) { return boardVertices[i]; }
	 
	 public Vertex[] getVertices() { return boardVertices; }
	 
	 public Path getPaths(int i) { return boardPaths[i]; }
	 
	 public Path[] getPaths() { return boardPaths; }
	 
	 public Hexagon getHexagons(int i) { return boardHexagons[i]; }
	 
	 public Hexagon[] getHexagons() { return boardHexagons; }
	 
	 public Hexagon getHexagon(char z) {
		 for (int k = 0; k < boardHexagons.length; k++) {
			 if (z == boardHexagons[k].getName()) {
				 return boardHexagons[k];
			 }
		 }
		 Hexagon ghost = new Hexagon();
		 return ghost;
	 }
	 
}
