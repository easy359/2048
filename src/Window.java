import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Window extends JPanel implements Runnable{


	private static final long serialVersionUID = 1L;
	//Instance variables the are used to create a new Grid and keyListener
	private Grid grid;
	private keyListener key;
	
	//Block array width and height
	private int width, height;
	
	//boolean used to check if the game is running
	public boolean running = false;
	
	//Used to check if new blocks should be 
	//incremented over time
	public boolean increment;
		
	//arrays that store the values of tileset images in the res folder
	public static Image[] tileset_number;
	public static Image tileset_gameOver;
	
	//thread to start the game
	private Thread thread;
	
	//constructor that sets values to all variables and starts a new thread
	public Window(int width, int height, keyListener key, boolean b, int value){
		
		if(width + height >= 20 && width >= 5 && height >= 5 && b == true){
			increment = true;
		}
		else{
			increment = false;
		}
		running = true;
		
		this.width = width;
		this.height = height;
		grid = new Grid(width, height, 16, 128, increment, value);
		this.key = key;		
		
		tileset_number = new Image[12];
		tileset_gameOver = new ImageIcon("res/gameOver.game.png").getImage();
		tileset_gameOver = createImage(new FilteredImageSource(tileset_gameOver.getSource(), new CropImageFilter(0,0,600,700)));
	
		for(int i = 0; i < tileset_number.length; i++){
			tileset_number[i] = new ImageIcon("res/numbers.game.png").getImage();
			tileset_number[i] = createImage(new FilteredImageSource(tileset_number[i].getSource(), new CropImageFilter(0,32*i,32,32)));
		}
		
		thread = new Thread(this);
		//the start() method calls the run() method
		thread.start();
	}
	
	//called by repaint(), this method checks to see if the game is still running
	//if running, calls the method draw(Graphics g) in Grid class to,
	//if not running, waits 500 milliseconds and displays the gameOver tileset
	public void paintComponent(Graphics g){
		if(running){
			g.setColor(Color.GRAY);
			//creates a background for the frame
			g.fillRect(0, 0, getWidth(), getHeight());
			grid.draw(g);
		}
		else if(!running){
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
				g.drawImage(tileset_gameOver, 0, 0, 150*width, 150*height, null);
		}
		running = isRunning();
	}

	//Returns the boolean isRunning() from the Grid class
	public boolean isRunning(){
		return grid.isRunning();
	}

	//creates a game loop and delays the thread
	public void run(){
		//spawns the first two random tiles
		grid.spawnRandomTile();
		grid.spawnRandomTile();
		//gameloop
		while(running){
			key.update();
			grid.physics(key);
			repaint();
			try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
		}
		//loop runs when game is over
		//used to display the gameOver tileset
		while(!running){
			repaint();
			try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}

