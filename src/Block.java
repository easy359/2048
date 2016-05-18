import java.awt.*;

public class Block extends Rectangle{

	private static final long serialVersionUID = 1L;
	//instance variable
	private int xSquare, ySquare;
	private int blockID;
	//Used to check if a block has combined with another block in the current update
	private boolean mixable;
	
	public Block(int xS, int yS, int x, int y, int width, int height, int blockID, Grid grid){
		this.xSquare = xS;
		this.ySquare = yS;
		this.blockID =  blockID;
		mixable = true;
		//allow to check coordinates of the block in other methods
		setBounds(x,y,width,height);
	}

	//Returns X position in blocks
	public int getXSquare(){
		return xSquare;
	}
	//Returns Y position in blocks
	public int getYSquare(){
		return ySquare;
	}
	//Return blockID *Value of the Block, used to tell what Block it is*
	public int getID(){
		return blockID;
	}
	//Sets the value of blockID = "id"
	public void setID(int id){
		blockID = id;
	}
	
	//Re-draws each block in blocks
	public void draw(Graphics g){
		g.drawImage(Window.tileset_number[blockID], x, y, width, height, null);
	}
	
	//Return instance variable mixable
	public boolean canMix(){
		return mixable;
	}
	
	//Changes the value of mixable to parameter b
	public void setMix(boolean b){
		mixable = b;
	}
	
	//Returns true if the block can move into an empty space or combine with an adjacent tile	
	public boolean canMove(int x, int y){
		int myID = blockID;
		if(isValidBlock(y,x+1)){
			if(Grid.blocks[y][x+1].blockID == myID || Grid.blocks[y][x+1].blockID == Value.nothing){
				return true;
			}
		}
		if(isValidBlock(y,x-1)){
			if(Grid.blocks[y][x-1].blockID == myID || Grid.blocks[y][x-1].blockID == Value.nothing){
				return true;
			}
		}
		if(isValidBlock(y+1,x)){
			if(Grid.blocks[y+1][x].blockID == myID || Grid.blocks[y+1][x].blockID == Value.nothing){
				return true;
			}
		}
		if(isValidBlock(y-1,x)){
			if(Grid.blocks[y-1][x].blockID == myID || Grid.blocks[y-1][x].blockID == Value.nothing){
				return true;
			}
		}
		return false;
	}	
	
	//Return true if the block is in the grid
	public boolean isValidBlock(int y, int x){
		if(x >= 0 && y >= 0 && x < Grid.worldWidth && y < Grid.worldHeight){
			return true;
		}else{

			return false;
		}
	}

	//Return true if the Blocks value is Value.nothing
	public boolean isRandomBlock(){
		boolean ret = false;
			if(isValidBlock(ySquare,xSquare) && blockID == Value.nothing){
				ret = true;
			}
		return ret;		
	  }


}
