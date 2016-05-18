import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * The Grid class is responsible for tracking each Block,					*
 *  drawing each Block, drawing the score, moving all Blocks				*
 *  based on user input, spawning and finding random blocks when needed,	*
 *  and checking if the game is still running								*
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class Grid {
	
	//Keep track of the score
	private String score;
	private int scoreCounter;
	//Keep track of increments
	public int moveCounter;
	private int value;
	
	//Stores different values of the grid, used for drawing
	public static int worldHeight, worldWidth, layoutSize, blockSize;
	//Creates a new Block array that stores all the values of the Blocks
	public static Block[][] blocks;
	
	public static Random rand;
	
	public boolean canIncrement;
	
	//Sets values to instance variable and fills Block array with empty tiles
	public Grid(int worldWidth,int worldHeight,int layoutSize, int blockSize, boolean b, int value){
		score = "0";
		scoreCounter = 0;
		this.value = value;
		canIncrement = b;
		Grid.worldWidth = worldWidth;
		Grid.worldHeight = worldHeight;
		Grid.layoutSize = layoutSize;
		Grid.blockSize = blockSize;	
		blocks = new Block[worldHeight][worldWidth];
		//Loop that fills the Block[][] and sets all values to = Value.nothing
		for(int y = 0; y < blocks.length; y++){
			for(int x = 0; x < blocks[0].length; x++){
				blocks[y][x] = new Block(x, y, (x * blockSize) + (layoutSize * x) + layoutSize, (y * blockSize) + (layoutSize * y) + layoutSize, blockSize, blockSize, Value.nothing, this);
			}
		}
		rand = new Random();
		moveCounter = 0;
	}

	//Tells each block in Block array to repaint itself
	//Displays the score
	public void draw(Graphics g) {
		for(int y = 0; y<blocks.length; y++){
			for(int x = 0; x<blocks[0].length; x++){
				blocks[y][x].draw(g);
			}
		}
		g.setColor(Color.WHITE);
		score = String.valueOf(scoreCounter);
		g.drawString("Score: " + score , 25, 155*worldHeight);
	}
	
	//Tells each block in the Block array to update
	//it's position based on user arrow key input
	public void physics(keyListener key) {
		if(key.up == true){
			moveAllBlocks(Value.up);
		}
		else if(key.down == true){
			moveAllBlocks(Value.down);
		}
		else if(key.left == true){
			moveAllBlocks(Value.left);
		}
		else if(key.right == true){
			moveAllBlocks(Value.right);
		}
	}

	//Moves all blocks with value other than 0, in the direction "direction", and combines
	//all blocks that touch when they are moves in the same direction
	//*A block can only be combined once per method call*
	public void moveAllBlocks(int direction){	
		//didMove is used to check if any values in blocks were changed
		boolean didMove = false;
		//Series of if/else statements to check direction and move all Blocks in blocks accordingly
		if(direction == Value.up){
			for(int i = worldHeight; i > 1; i--){
				for(int y = 1; y < i; y++){
					for(int x = 0; x < blocks[0].length; x++){
						if(blocks[y-1][x].getID() == blocks[y][x].getID() && blocks[y][x].getID() != Value.nothing && blocks[y-1][x].canMix() && blocks[y][x].canMix()){
							scoreCounter += Math.pow(2, blocks[y][x].getID()+1);
							blocks[y-1][x].setID(blocks[y][x].getID()+1);
							//setMix makes sure no block is combined twice in one method call
							blocks[y-1][x].setMix(false);
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
						else if(blocks[y][x].getID() != Value.nothing && blocks[y-1][x].getID() == Value.nothing){
							blocks[y-1][x].setID(blocks[y][x].getID());
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
					}
				}
			}
		}
		else if(direction == Value.down){
			for(int i = -1; i < worldHeight - 2; i++){
				for(int y = worldHeight-2; y > i; y--){
					for(int x = 0; x < blocks[0].length; x++){
						if(blocks[y+1][x].getID() == blocks[y][x].getID() && blocks[y][x].getID() != Value.nothing && blocks[y+1][x].canMix() && blocks[y][x].canMix()){
							scoreCounter += Math.pow(2, blocks[y][x].getID()+1);
							blocks[y+1][x].setID(blocks[y][x].getID()+1);
							blocks[y+1][x].setMix(false);
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
						else if(blocks[y][x].getID() != Value.nothing && blocks[y+1][x].getID() == Value.nothing){
							blocks[y+1][x].setID(blocks[y][x].getID());
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
					}
				}
			}
		}
		else if(direction == Value.left){
			for(int i = worldWidth; i > 1; i--){
				for(int y = 0; y < blocks.length; y++){
					for(int x = 1; x < i; x++){
						if(blocks[y][x-1].getID() == blocks[y][x].getID() && blocks[y][x].getID() != Value.nothing && blocks[y][x-1].canMix() && blocks[y][x].canMix()){
							scoreCounter += Math.pow(2, blocks[y][x].getID()+1);
							blocks[y][x-1].setID(blocks[y][x].getID()+1);
							blocks[y][x-1].setMix(false);
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
						else if(blocks[y][x].getID() != Value.nothing && blocks[y][x-1].getID() == Value.nothing){
							blocks[y][x-1].setID(blocks[y][x].getID());
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
					}
				}
			}
		}
		else if(direction == Value.right){
			for(int i = -1; i < worldWidth-2; i++){
				for(int y = 0; y < blocks.length; y++){
					for(int x = worldWidth - 2; x > i; x--){
						if(blocks[y][x+1].getID() == blocks[y][x].getID() && blocks[y][x].getID() != Value.nothing && blocks[y][x+1].canMix() && blocks[y][x].canMix()){
							scoreCounter += Math.pow(2, blocks[y][x].getID()+1);
							blocks[y][x+1].setID(blocks[y][x].getID()+1);
							blocks[y][x+1].setMix(false);
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
						else if(blocks[y][x].getID() != Value.nothing && blocks[y][x+1].getID() == Value.nothing){
							blocks[y][x+1].setID(blocks[y][x].getID());
							blocks[y][x].setID(Value.nothing);
							didMove = true;
						}
					}
				}
			}
		}
		//switchCan sets the instance value of "mixable" to true for all Blocks in blocks
		switchCanMix(true);
		//spawns another tile if any values in blocks were changed
		if(didMove)
			spawnRandomTile();
	}
	
	//Finds all possible locations a Block can be placed and chooses one at random
	//The Block chosen has a 90% chance of having a value of Value.two and a 10%chance of having a value of Value.four
	public void spawnRandomTile(){
		Block[] possibleBlocks = findRandomBlocks();
		if(possibleBlocks.length > 0){
			int random = rand.nextInt(possibleBlocks.length);
			int xC = possibleBlocks[random].getXSquare();
			int yC = possibleBlocks[random].getYSquare();
			int randNumber = rand.nextInt(9);
			if(randNumber < 8){
				//Checks if the amount of moves is greater than 'value'
				//Increments the value of the new block accordingly
				int add = 1;
				if(canIncrement && moveCounter >= value){
					add += (moveCounter/value);
				}
				blocks[yC][xC] = new Block(xC, yC,(int) blocks[yC][xC].getX(), (int) blocks[yC][xC].getY(), blockSize, blockSize, add, this); 
			}else if(randNumber == 8){
				int add = 2;
				if(canIncrement && moveCounter >= value){
					add += (moveCounter/value);

				}
				blocks[yC][xC]= new Block(xC, yC,(int) blocks[yC][xC].getX(), (int) blocks[yC][xC].getY(), blockSize, blockSize, add, this); ;
			}
			moveCounter++;
		}
	}
	
	//Finds all empty locations in blocks
	//*empty is equivalent to Value.nothing8
	public Block[] findRandomBlocks(){
		//temp array to store empty location
		Block[] temp = new Block[worldWidth*worldHeight];
		//counts the number of empty location
		int counter = 0;
		for(int y = 0; y <blocks.length; y++){
			for(int x = 0; x <blocks[0].length; x++){
				if((blocks[y][x]).isRandomBlock()){
					temp[counter] = blocks[y][x];
					counter++;
				}
			}
		}
		//return array to store all location in temp array, but had the correct size
		Block[] ret = new Block[counter];
		for(int i = 0; i < ret.length;  i++){
			ret[i] = temp[i];
		}
		return ret;
	}
	
	//sets the value of mixable of all blocks equal to "b"
	public void switchCanMix(boolean b){
		for(int y = 0; y < blocks.length; y++){
			for(int x = 0; x < blocks[0].length; x++){
				blocks[y][x].setMix(b);
			}
		}
	}
	
	//Calls the canMove() method from the Block Class
	//and returns true if any Block canMove()
	public boolean isRunning() {
		for(int y = 0; y < blocks.length; y++){
			for(int x = 0; x < blocks[0].length; x++){
				if(blocks[y][x].canMove(x, y))
					return true;
			}
		}
		return false;
	}
	
	
}
