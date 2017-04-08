package com.tetris.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class Tetronimo {
	
	private final Point spawnCoord = new Point(5,2);
	private Point currentCoord;
	private int currentTetronimoIndex, currentTetronimoOrientation;
	private Point[] currentPiece;
	private Color currentColor;
	
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
    	currentCoord = new Point(spawnCoord.x, spawnCoord.y);
    	currentColor = tetronimoColor[currentTetronimoIndex - 1];
    	
    	return currentPiece;
    }
    
	/**
	 * Paints the Tetronimo using a global point as a reference and a point array for spatial arrangement
     * of a unique piece.
	 * @param g
	 */
	public void paintPiece(Board b, Graphics2D g) {
		int currX, currY;
		for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x + currentCoord.x;
    		currY = currentPiece[i].y + currentCoord.y;
    		g.setColor(currentColor);
    		g.fillRect((currX * 26),
    				(currY * 26), 
    				25, 25);
    	}
	}
	
	/**
	 * Un-Paints the Tetronimo using a global point as a reference and a point array for spatial arrangement
     * of a unique piece.
	 * @param g
	 */
	public void unpaintPiece(Board b, Graphics2D g) {
		int currX, currY;
		for (int i = 0; i < currentPiece.length; i++) {
    		currX = currentPiece[i].x + currentCoord.x;
    		currY = currentPiece[i].y + currentCoord.y;
    		g.setColor(b.getBoardColor());
    		g.fillRect((currX * 26),
    				(currY * 26), 
    				25, 25);
    	}
	}
    
    // TODO: Need to make it so that it generates a bag of the 7 pieces.
    /**
     * Generates a random number as an index to be used for the shapeTable which holds the data arrays
     * for each tetronimo
     * @return int
     */
    public int generateTetronimoIndex() {
    	int n = (int) ((Math.random() * 7) + 1);
    	
    	switch(n) {
    	case 1:
    		return 1;
    	case 2:
    		return 2;
    	case 3:
    		return 3;
    	case 4:
    		return 4;
    	case 5:
    		return 5;
    	case 6:
    		return 6;
    	case 7:
    		return 7;
    	}
    	return -1;
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
	public Color getCurrentColor() {
		return currentColor;
	}
}
