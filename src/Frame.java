import java.awt.GridLayout;

import javax.swing.JFrame;


public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//Creates new Frame and sets important value
	//adds "key" and a new window to the frame
	public Frame(int width, int height, keyListener key, boolean b, int value){
		setTitle("2048");
		setSize(150 * width, 165 * height);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1,0,0));
		Window window = new Window(width, height, key, b, value);
		add(window);
		addKeyListener(key);
		setVisible(true);
	}
	
	
	//Main method that start the game
	//creates a new keyListener for user inputs
	//created a new Frame for displaying the game
	public static void main(String[] args){
		keyListener key = new keyListener();
		//First two parameters change the size of the game
		//When the fourth parameter = true, new tiles will 
		//will increment in value every '5th parameter' moves
		new Frame(4, 4, key, true, 5);
	}
}
