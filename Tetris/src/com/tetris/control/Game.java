package com.tetris.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private void init() {
		setSettings();
		addKeyListeners();
		addComponents();
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
					System.out.println("down");
					break;
				case KeyEvent.VK_LEFT:
					System.out.println("left");
					break;
				case KeyEvent.VK_RIGHT:
					System.out.println("right");
					break;
				case KeyEvent.VK_SPACE:
					System.out.println("space");
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
				System.out.println("Start");
				remove(startButton);
				remove(highScoreButton);
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
		while(running) {
			
			// Run logic
			// for board DS, get the current piece.
			
			// Paint and Render
			BufferStrategy buf = getBufferStrategy();
			if (buf == null) {
				createBufferStrategy(3);
				continue;
			}
			Graphics2D g = (Graphics2D) buf.getDrawGraphics();
			render(g);
			buf.show();
			try {
				t.sleep(1000L);
				// drop here:  drop()
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the visual details for the Graphics2D object to display on the UI
	 * @param g
	 */
	private void render(Graphics2D g) {
		board.paintBoard(g);
		tetronimo.paintTetronimo(g);
		tetronimo.drop(g, board);					// MOVE DROP UNDER SLEEP THREAD
		
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
