package f2.spw;   

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends Sprite implements Obstacle{

    public static final int Y_TO_DIE = 600;
	int step = 12; 
    private boolean alive = true;
    

	public Block(int x, int y) {
		super(x, y, 30, 30);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.PINK);                                       
		g.fillRect(x, y, width, height);
		
	}

	public void proceed(){                                            
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
        }
        
    }

    public boolean isAlive(){
		return alive;
    }
    
    
	public boolean isGoodOrNot(){
		return true;
	}
}

