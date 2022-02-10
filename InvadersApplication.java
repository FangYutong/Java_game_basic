import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {

	private static final Dimension WindowSize = new Dimension(600,600);
	private static final int NUMALIENS = 30;
	private Sprite2D[] AlienArray = new Sprite2D[NUMALIENS];
	private Sprite2D PlayerShip;
	private boolean isInitialised = false;
	private static String workingDirectory;
	
	// to solve screen flicker
	private BufferStrategy strategy;
	
	//Constructor
	public InvadersApplication() {
		//Create and set up the window
		this.setTitle("Threads and Animation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Display the window, centred on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setVisible(true);
		
		// to solve flicker
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		//Instantiates the GameObject instances
		ImageIcon icon = new ImageIcon(workingDirectory + "/alien_ship.png");
		Image alienImage = icon.getImage();
		
		for (int i = 0; i < NUMALIENS; i++) {
			AlienArray[i] = new Sprite2D(alienImage);
		}

		icon = new ImageIcon(workingDirectory + "/player_ship.png");
		Image shipImage = icon.getImage();
		PlayerShip = new Sprite2D(shipImage);
		PlayerShip.setPosition(300, 550);
		
				
		//Create and Start a thread
		Thread t = new Thread(this);
		t.start();
		
		addKeyListener(this);
		isInitialised = true;
	}

	//Thread's entry point
	public void run() {
		//Sleep for 1/50 sec
		while(true) {
			
			// why always use try&catch in run method?
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Animate game objects
			for (int i = 0; i < NUMALIENS; i++) {
				AlienArray[i].moveEnemy();
			}
			PlayerShip.movePlayer();
			
			// force the application to repaint
			this.repaint();
		}
	}
	
	//Three Keyboard Event-Handler functions
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			PlayerShip.setXSpeed(2);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			PlayerShip.setXSpeed(-2);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		PlayerShip.setXSpeed(0);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	//Application's paint method
	public void paint(Graphics g) {
		if (isInitialised) {
			
			g = strategy.getDrawGraphics();
			
			// clear the canvas with big black rectangle
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 600, 600);
			
			for (int i = 0; i < NUMALIENS; i++) {
				AlienArray[i].paint(g);
			}
			PlayerShip.paint(g);
			strategy.show();
		}
		
		
	}
	
	//Application entry point
	public static void main(String[] args) {
		workingDirectory = System.getProperty("user.dir");
		InvadersApplication w = new InvadersApplication();
	}
}