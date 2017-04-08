package com.tetris.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.tetris.model.Board;
import com.tetris.model.Tetronimo;

/*
 * The JFrame of the Game is basically where all of the listener events should take place
 * This is the main part of the running game where the threads and listeners should be held
 */
public class Game extends JFrame implements Runnable {
	
	final int 
	WIDTH = 26*12, HEIGHT = 26*22, 
	BUTTONWIDTH = 160, BUTTONHEIGHT = 20,
	BUFFERSPACE = 40;
	private Board board;
	private Tetronimo tetronimo;
	private Thread t;
	private Graphics2D g;
	BufferStrategy buf;
	
	// Constructor
	public Game() {
		board = new Board();
		tetronimo = new Tetronimo();
		t = new Thread(this);
		
		init();
	}
	
	/**
	 * Initializes J-Objects and adds the to the main JFrame used for the UI
	 */
	private void init(){
		setSettings();
		addKeyListeners();
		addComponents();
	}
	
	/**
	 * Initializes the board and first tetronimo piece to be painted on the board
	 */
	private void initBoard(){
		board.initBoard(g);
	}
	
	/**
	 * Sets the JFrame fields to the specified settings
	 */
	private void setSettings() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setTitle("Tetris");
		getContentPane().setBackground(Color.decode("#b0e0e6"));
		setLayout(null);
	}
	
	/**
	 * Adds Key Listeners to the main game JFrame
	 */
	private void addKeyListeners() {
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					System.out.println("up");
					break;
				case KeyEvent.VK_DOWN:
					drop(board);
					break;
				case KeyEvent.VK_LEFT:
					//System.out.println("left");
					move(board, e);
					break;
				case KeyEvent.VK_RIGHT:
					//System.out.println("right");
					move(board, e);
					break;
				case KeyEvent.VK_SPACE:
					System.out.println("space");
					break;
				// hold
				case KeyEvent.VK_SHIFT:
					break;
				case KeyEvent.VK_ESCAPE:
					exit();
					break;
				} 
			}
			public void keyReleased(KeyEvent e) {
			}
		});
	}
	
	/**
	 * Adds buttons and other components for the main JFrame
	 */
	private void addComponents() {
		JLabel title = new JLabel("Tetris");
		title.setFont(new Font("Calibri", Font.BOLD, 30));
		title.setBounds(120, 10, 150, 20); 
		add(title);
		
		
		
		// Establish Start Button
		JButton startButton = new JButton("Start");
		startButton.setBounds(WIDTH/2 - BUTTONWIDTH/2, HEIGHT/2, 
				BUTTONWIDTH, BUTTONHEIGHT);
		
		// Establish High Score Button
		JButton highScoreButton = new JButton("High Scores");
		highScoreButton.setBounds(WIDTH/2 - BUTTONWIDTH/2, HEIGHT/2 + BUFFERSPACE, 
				BUTTONWIDTH, BUTTONHEIGHT);

		// Start Button Action Listener
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(startButton);
				remove(highScoreButton);
				removeAll();
				start();
			}
		});
		
		// High Score Action Listener
		highScoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("To Be Implemented");
			}
		});
		
		startButton.setFocusable(false);
		highScoreButton.setFocusable(false);
		add(startButton);
		add(highScoreButton);
	}
	
	/**
	 * Starts the thread for the main application
	 * will run method run()
	 */
	public void start() {
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	
	/**
	 * Stops the running thread for Tetris and exits the program.
	 */
	public void exit() {
		System.exit(0);
	}
	
	@Override
	public void run() {
		
		boolean running = true;
		boolean init = false;
		buf = getBufferStrategy();
		while (buf == null) {
			buf = getBufferStrategy();
			System.out.println("fkme");
			createBufferStrategy(3);
		}
		g = (Graphics2D) buf.getDrawGraphics();
		if (!init) {
			initBoard();
			init = true;
		}
		
		/*
		 * Two timers were used to help avoid a sleep issue with repainting objects as they moved
		 * With drop falling at a certain (slow) rate, the piece movement will not update instantly. Only updated
		 * on the GUI with the drop rate.
		 */
		Timer timer = new Timer();
		timer.schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	render(g);
		        		buf.show();
		            }
		        }, 0,
		        1 
		);
		Timer timer2 = new Timer();
		timer2.schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	drop(board);
		            }
		        }, 1000,
		        1000 
		);
	}

	/**
	 * Sets the visual details for the Graphics2D object to display on the UI
	 * @param g
	 */
	private void render(Graphics2D g) {
		if (g == null)
			return;
		board.paintBoard(g);
		tetronimo.paintPiece(board, g);
	}
	
	/**
	 * Moves the current piece based on the key event input, if able to do so
	 * @param b
	 * @param e
	 */
	private void move(Board b, KeyEvent e) {
		if (e == null || b == null)
			return;
		int k = e.getKeyCode();
		int move = 0;
		int currX, currY;
    	boolean flag = true;
		switch (k) {

		// fast fall
		case KeyEvent.VK_DOWN:
			break;
		// move left
		case KeyEvent.VK_LEFT:
	    	move = -1;
			break;
		// move right
		case KeyEvent.VK_RIGHT:
			move = 1;
			break;
		// instadrop
		case KeyEvent.VK_SPACE:
			break;
		} 
		
    	Point[] currPiece = tetronimo.getCurrentPiece();
    	for (int i = 0; i < currPiece.length; i++) {
			currX = currPiece[i].x + tetronimo.getCurrentCoord().x;
			currY = currPiece[i].y + tetronimo.getCurrentCoord().y;
			if (hasCollision(currX + move, currY, b))
				flag = false;
		}
    	
    	// If no collision, move the piece. Remove the piece in the current state, will move position.
    	if (flag) {
    		tetronimo.getCurrentCoord().x = tetronimo.getCurrentCoord().x + move;
    		repaint();
    	}
	}
	
    /**
     * Drops the piece after one time iteration of the game.
     * @param g
     * @param b
     */
    private void drop(Board b) {
    	int currX, currY;
    	boolean flag = true;
    	
    	Point[] currPiece = tetronimo.getCurrentPiece();
    	for (int i = 0; i < currPiece.length; i++) {
			currX = currPiece[i].x + tetronimo.getCurrentCoord().x;
			currY = currPiece[i].y + tetronimo.getCurrentCoord().y;
			if (hasCollision(currX, currY + 1, b))
				flag = false;
		}
    	
    	// If no collision, drop the piece. Remove the piece in the current state, will drop a position down
    	if (flag) {
    		tetronimo.getCurrentCoord().y = tetronimo.getCurrentCoord().y + 1;
    	}
    	else {
    		attachToWell(b);
    		// 0. Set the piece as a part of the well x
    		// 1. Check if line(s) can be cleared
    		// 2. Create a new piece x
    		// 3. Reset the location x
    		tetronimo = new Tetronimo();
    	}
    	repaint();
    }
    
    /**
     * Returns a boolean value on whether the x and y position given is the color of the board or not.
     * Return false if the color at the given position is boardcolor.
     * Return true if the color at the given position is not boardcolor.
     * @param x
     * @param y
     * @param b
     * @return
     */
    public boolean hasCollision(int x, int y, Board b) {
    	if (b == null)
    		return true;
    	
		if(b.getBoard()[x][y].equals(b.getBoardColor())) 
			return false;
		else
			return true;
    }
    
    /**
     * Adds the piece to the board if the piece can no longer drop,
     * @param b
     */
    private void attachToWell(Board b) {
    	int currX, currY;
    	Point[] currPiece = tetronimo.getCurrentPiece();
    	for (int i = 0; i < currPiece.length; i++) {
			currX = currPiece[i].x + tetronimo.getCurrentCoord().x;
			currY = currPiece[i].y + tetronimo.getCurrentCoord().y;
			b.getBoard()[currX][currY] = tetronimo.getCurrentColor();
		}
    }
	
	
	// Getters and Setters
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Tetronimo getTetronimo() {
		return tetronimo;
	}
	public void setTetronimo(Tetronimo tetronimo) {
		this.tetronimo = tetronimo;
	}
	public Thread getThread() {
		return t;
	}
	public Graphics2D getGraphics2D() {
		return g;
	}

}
