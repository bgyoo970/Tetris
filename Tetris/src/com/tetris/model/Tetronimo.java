package com.tetris.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Tetronimo {
	
	private Point spawnCoord = new Point(5,2);
	private Point currentCoord;
	private int currentTetronimoIndex, currentTetronimoOrientation;
	private Point[] currentPiece;
	
	public enum Tetrominoes { NoShape, ZShape, SShape, LineShape, 
        TShape, SquareShape, LShape, MirroredLShape };
        
	private Color[] tetronimoColor = {
				Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};
	
	public Point[][][] shapeTable = new Point[][][] {
		// 0. No Shape
		{
			{ new Point(0, 0),   new Point(0, 0),   new Point(0, 0),   new Point(0, 0) },
		},
		// 1. S-Shape
		{
			{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
			{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
			{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
			{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
		},
		// 2. Z-Shape
		{
			{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
			{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
		},
		// 3. I-Shape
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
		},
		// 4. T-Shape
		{
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
			{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
			{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
		},
		// 5. O-Shape
		{
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
			{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
		},
		// 6. L-Shape
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
		},
		// 7. J-Shape
		{
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
			{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
			{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
		}
    };
    
    public Point[] generateTetronimo() {
    	currentTetronimoIndex = generateTetronimoIndex();
    	currentTetronimoOrientation = 0;
    	currentPiece = shapeTable[currentTetronimoIndex][currentTetronimoOrientation];
    	
    	return currentPiece;
    }
    
    public void paintTetronimo(Graphics2D g, int x, int y) {
    	// Generate Random Piece Index
    	currentTetronimoIndex = generateTetronimoIndex();
    	currentTetronimoOrientation = 0;
    	if (currentTetronimoIndex == -1)
			try { throw new Exception("Something went wrong");}
    		catch (Exception e) {e.printStackTrace();}
    	
    	// Select corresponding color and piece
		g.setColor(tetronimoColor[currentTetronimoIndex - 1]);
		
		for (Point p : shapeTable[currentTetronimoIndex][currentTetronimoOrientation])
		g.fillRect((p.x + spawnCoord.x) * 26, 
				   (p.y + spawnCoord.y) * 26, 
				   25, 25);
    }
    
    public void paintTetronimo(Graphics2D g) {
    	// Generate Random Piece Index
    	currentTetronimoIndex = generateTetronimoIndex();
    	currentTetronimoOrientation = 0;
    	if (currentTetronimoIndex == -1)
			try { throw new Exception("Something went wrong");}
    		catch (Exception e) {e.printStackTrace();}
    	
    	// Select corresponding color and piece
		g.setColor(tetronimoColor[currentTetronimoIndex - 1]);
		
		for (Point p : shapeTable[currentTetronimoIndex][currentTetronimoOrientation])
		g.fillRect((p.x + spawnCoord.x) * 26, 
				   (p.y + spawnCoord.y) * 26, 
				   25, 25);
    }
    
    public int generateTetronimoIndex() {
    	int n = (int) ((Math.random() * 20) + 1);
    	
    	// s-shape 20%
    	if ((n >= 1) && (n <= 4))
    		return 1;
    	// z-shape 20%
    	if ((n >= 5) && (n <= 8))
    		return 2;
    	// line-shape 5%
    	if (n == 9)
    		return 3;
    	// t-shape 10%
    	if ((n == 10) || (n == 11))
    		return 4;
    	// o-shape 20%
    	if ((n >= 12) && (n <= 14))
    		return 5;
    	// l-shape 15%
    	if ((n >= 15) && (n <= 17))
    		return 6;
    	// j-shape 15%
    	if ((n >= 18) && (n <= 20))
    		return 7;
    	
    	return -1;
    }
    public void drop(Board b, Point[] currentPiece) {
    	int currX, currY;
    	boolean flag;
    	for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x;
    		currY = currentPiece[i].y;
    		if (hasCollisionAt(currX, currY + 1, b))
    			flag = false;
    	}
    	if (!hasCollisionAt(spawnCoord.x, spawnCoord.y + 1, b))
    		spawnCoord.y = spawnCoord.y + 1;
    	
    	//else place piece, reset spawnCoord.
    }
    
    public void drop(Board b) {
    	if (!hasCollisionAt(spawnCoord.x, spawnCoord.y + 1, b))
    		spawnCoord.y = spawnCoord.y + 1;
    	
    	//else place piece, reset spawnCoord.
    }
    
    public boolean hasCollisionAt(int x, int y, Board b) {
		if(b.board[x][y] == b.BOARDCOLOR) {
			System.out.println("color: "+ b.board[x][y]);
    		return false;
		}
    	else {
			System.out.println("color: "+ b.board[x][y]);
    		return true;
    	}
    }
    
    public Point getSpawnCoord(){
    	return spawnCoord;
    }
    public Point getCurrentCoord(){
    	return currentCoord;
    }
    public int getCurrentTetronimoIndex() {
    	return currentTetronimoIndex;
    }
    public int getCurrentTetronimoOrientation() {
    	return currentTetronimoOrientation;
    }
    public Point[] getCurrentPiece() {
    	return currentPiece;
    }
}
