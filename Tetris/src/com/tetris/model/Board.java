package com.tetris.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class Board {
	
	//java.awt.Color[r=52,g=90,b=191]
	private final Color BORDERCOLOR = Color.decode("#b0e0e6");
	//java.awt.Color[r=176,g=224,b=230]
	private final Color BOARDCOLOR = Color.decode("#345abf");
	private Color board[][];
	
	public Board() {
		board = new Color[12][23];
	}
	
	/**
	 * Initializes the board to an empty well
	 * Paints the view for the user and also sets the underlying data in the board array.
	 * @param g
	 */
	public void initBoard(Graphics2D g) {
		g.fillRect(0, 0, 26*12, 26*23);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				// Fill in the borders
				if ((j < 2) || (j > 20) || (i < 1) || (i > 10)) {
					// Graphics
					g.setColor(BORDERCOLOR);
					g.fillRect(26*i, 26*j, 25, 25);
					// Board
					board[i][j] = BORDERCOLOR;
				}
				// Fill in the board
				else {
					// Grapics
					g.setColor(BOARDCOLOR);
					g.fillRect(26*i, 26*j, 25, 25);
					// Board
					board[i][j] = BOARDCOLOR;
				}
			}
		}
	}
	
	/**
	 * Paints the board according to the colors assigned in the board array.
	 * @param g
	 */
	public void paintBoard(Graphics2D g) {
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				// Fill in the borders
				if ((j < 2) || (j > 20) || (i < 1) || (i > 10)) {
					// Graphics
					g.setColor(BORDERCOLOR);
					g.fillRect(26*i, 26*j, 25, 25);
					// Board
					board[i][j] = BORDERCOLOR;
				}
				// Fill in the board
				else {
					// Graphics
					Color color = board[i][j];
					g.setColor(color);
					g.fillRect(26*i, 26*j, 25, 25);
				}
			}
		}
	}
	
	
	// Getters
	public Color[][] getBoard() {
		return board;
	}
	public Color getBorderColor() {
		return BORDERCOLOR;
	}
	public Color getBoardColor() {
		return BOARDCOLOR;
	}
}
