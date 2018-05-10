package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Block> blocks = new ArrayList<Block>();	
	private SpaceShip v1;
	private SpaceShip v2;	
	
	private Timer timer;
	
	private long score = 0;
	private int time = 0;
	private double difficulty = 0.01;
	
	public GameEngine(GamePanel gp, SpaceShip v1 , SpaceShip v2) {
		this.gp = gp;
		this.v1 = v1;		
		this.v2 = v2;

		gp.sprites.add(v1); 
		gp.sprites.add(v2);

		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateBlock(){
		Block e = new Block((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		blocks.add(e);
	}
	
	private void process(){
		time += 50; 
		if(time % 5000 == 0){  
			difficulty += 0.01;
		}


		if(Math.random() < difficulty || enemies.size() == 0 ){   				
			generateEnemy();
		}

		if(Math.random() < difficulty/10 ){ 
			generateBlock();
		}
		

		Iterator<Enemy> e_iter = enemies.iterator();        
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){ 
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}

		Iterator<Block> e_iter2 = blocks.iterator();        
		while(e_iter2.hasNext()){
			Block e = e_iter2.next();
			e.proceed();
			
			if(!e.isAlive()){ 
				e_iter2.remove();
				gp.sprites.remove(e);
			}
		}

		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr1) || er.intersects(vr2)){ 
				if (e.isGoodOrNot()){
					score += 500;
					gp.sprites.remove(e);
				}
				else{
					die();
					return;
				}
			}
		}
		for(Block e : blocks){
			er = e.getRectangle();
			if(er.intersects(vr1) || er.intersects(vr2)){ 
				if (e.isGoodOrNot()){
					score += 500;
					gp.sprites.remove(e);
				}
				else{
					die();
					return;
				}	
			}
		}
	}
	
	public void die(){ 
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v1.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v1.move(1);
			break;
		case KeyEvent.VK_D:  
			v2.move(1);
			break;
		case KeyEvent.VK_A:  
			v2.move(-1);
			break;	
		}
	}

	public long getScore(){
		return score;
	}

	public int getTime(){
		return time/1000;
	}
	public int getLevel(){
		return  (int)(difficulty*100); 
	}

	@Override
	public void keyPressed(KeyEvent e) {   
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {  
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {   
		//do nothing		 
	}
}
