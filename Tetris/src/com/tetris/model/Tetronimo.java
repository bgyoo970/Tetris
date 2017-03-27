package com.tetris.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Tetronimo {
	
	private final Point spawnCoord = new Point(5,2);
	private Point currentCoord;
	private int currentTetronimoIndex, currentTetronimoOrientation;
	private Point[] currentPiece;
	public enum Tetrominoes { NoShape, ZShape, SShape, LineShape, 
        TShape, SquareShape, LShape, MirroredLShape };
        
	private Color[] tetronimoColor = {
				Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};
	
	private final Point[][][] shapeTable = new Point[][][] {
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
    
    // Constructor
    public Tetronimo() {
    	currentPiece = generateTetronimo();
    	currentCoord = new Point(spawnCoord.x, spawnCoord.y);
    }
    
    /**
     * Creates a tetronimo by generating a piece randomly.
     * The method returns a point array which represents the spatial arrangement of the unique piece
     * @return Point array
     */
    public Point[] generateTetronimo() {
    	currentTetronimoIndex = generateTetronimoIndex();
    	currentTetronimoOrientation = 0;
    	currentPiece = shapeTable[currentTetronimoIndex][currentTetronimoOrientation];
    	return currentPiece;
    }
    
    // TODO: Need to let the underlying color board know that the pieces exist in the spot, as well as rendering it.
    /**
     * Paints the Tetronimo using a global point as a reference and a point array for spatial arrangement
     * of a unique piece.
     * @param g
     */
    public void paintTetronimo(Graphics2D g) {
    	if (currentTetronimoIndex == -1)
			try { throw new Exception("Something went wrong");}
    		catch (Exception e) {e.printStackTrace();}
    	
    	// Select corresponding color and piece
		g.setColor(tetronimoColor[currentTetronimoIndex - 1]);
		
		int currX, currY;
    	for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x;
    		currY = currentPiece[i].y;
    		g.fillRect(((currX + currentCoord.x) * 26),
    				((currY + currentCoord.y) * 26), 
    				25, 25);
    	}
    }
    
    /**
     * Unpaints the Tetronimo using a global point as a reference and a point array for spatial arrangement
     * of a unique piece.
     * @param g
     */
    public void unpaintTetronimo(Graphics2D g) {
    	if (currentTetronimoIndex == -1)
			try { throw new Exception("Something went wrong");}
    		catch (Exception e) {e.printStackTrace();}
    	Board b = new Board();
    	
    	// Select corresponding color and piece
		g.setColor(b.BOARDCOLOR);
		
		int currX, currY;
    	for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x;
    		currY = currentPiece[i].y;
    		g.fillRect(((currX + currentCoord.x) * 26),
    				((currY + currentCoord.y) * 26), 
    				25, 25);
    	}
    }
    
    /**
     * Generates a random number as an index to be used for the shapeTable which holds the data arrays
     * for each tetronimo
     * @return int
     */
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
    
    // TODO: Move this method over to Game.java
    /**
     * Drops the piece after one time iteration of the game.
     * @param g
     * @param b
     */
    public void drop(Graphics2D g, Board b) {
    	int currX, currY;
    	boolean flag = true;
    	
    	/*
    	 *  Need to unpaint tetronimo to check if the space the block
    	 *  wants to move to is the color of the board or not.
    	 *  If the color is not the color of the board, collision is detected
    	 */
    	unpaintTetronimo(g);
    	for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x + currentCoord.x;
    		currY = currentPiece[i].y + currentCoord.y;
    		if (hasCollisionAt(currX, currY + 1, b))
    			flag = false;
    	}
    	paintTetronimo(g);
    	
    	// If no collision, drop the piece
    	if (flag)
    		currentCoord.y = currentCoord.y + 1;
    	else {
    		// 0. Set the piece as a part of the well
    		// 1. Check if line(s) can be cleared
    		// 2. Create a new piece
    		// 3. Reset the location
    		currentPiece = generateTetronimo();
    		currentCoord.setLocation(spawnCoord.x, spawnCoord.y);
    	}
    }
    
    public boolean hasCollisionAt(int x, int y, Board b) {
		if(b.board[x][y] == b.BOARDCOLOR)
    		return false;
    	else
    		return true;
    }
    
    // Getters
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
    public Point[][][] getShapeTable() {
    	return shapeTable;
    }
}
