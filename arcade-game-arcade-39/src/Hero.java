import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * class for the hero
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Hero extends Character{
	
	private int life, score;
	private boolean keyPressed;
	private boolean fireReady;
	private boolean isUndefeatable;
	private int timeInterval;
	private BufferedImage imgLeft;
	private BufferedImage imgRight;
	private BufferedImage imgFall;
	private StopWatch timer;

	public Hero(Level level, Point2D.Double centerPoint) {
		super(level, centerPoint);
		this.reset();
		this.speed = 0;
		this.isUndefeatable = false;
		this.fireReady=false;
		this.keyPressed=false;
		this.timeInterval=5;
		try {
		    this.imgLeft = ImageIO.read(new File("heroleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Hero left image not found.");
		}
		try {
		    this.imgRight = ImageIO.read(new File("heroright.png"));
		} catch (IOException e) {
			System.err.print("Error! Hero right image not found.");
		}
		try {
		    this.imgFall = ImageIO.read(new File("herofall.png"));
		} catch (IOException e) {
			System.err.print("Error! Hero fall image not found.");
		}
		this.timer = new StopWatch();
	}
	
	public int getLife() {
		return this.life;
	}
	
	public boolean isAlive() {
		return this.life > 0;
	}
	
	public void reset() {
		this.life = 10;
		this.score = 0;
	}
	
	public void moveLeft() {
		this.speed = -Level.HERO_SPEED;
		this.isMovingLeft=true;
	}

	public void moveRight() {
		this.speed = Level.HERO_SPEED;
		this.isMovingLeft=false;
	}
	
	// draw the hero with three different forms. picture provided by Ao Liu, special thanks 
	@Override
	public void drawOn(Graphics2D g) {
		long time = System.currentTimeMillis();
		long quarterSeconds = time / 250;
		if (quarterSeconds % 3 == 0 && this.isUndefeatable)
			return;
		if (isMovingLeft && onPlatform)
			g.drawImage(imgLeft, null, (int) this.centerPoint.getX(), (int) this.centerPoint.getY());
		else if (!isMovingLeft && onPlatform) 
			g.drawImage(imgRight, null, (int) this.centerPoint.getX(), (int) this.centerPoint.getY());

		else if (!onPlatform) 
			g.drawImage(imgFall, null, (int) this.centerPoint.getX(), (int) this.centerPoint.getY());
		
	}
	
	// these methods cancel the delay of keyboard for shift and fire
	@Override 
	public void shift() {
		if (!keyPressed) {
			speed=0;
		}
		super.shift();
	}
	
	public void keyPressed() {
		keyPressed=true;
	}
	
	public void keyNotPressed() {
		keyPressed=false;
	}
	
	public void setFire() {
		this.fireReady=true;
	}
	
	public void setNotFire() {
		this.fireReady=false;
	}
	
	// fire the bubble with time interval of 5
	@Override
	public void fire() {
		if (this.fireReady && this.onPlatform && this.timeInterval<=0) {
			Point2D.Double point = new Point2D.Double(centerPoint.getX(), centerPoint.getY() - 10);
			Bubble newBubble = new Bubble(level,point,this.isMovingLeft);
			newBubble.addToLevel();
			this.timeInterval=5;
		}
		this.timeInterval--;
	}
	
	@Override
	public void die() {
		if (!this.isUndefeatable) {
			this.level.restart();
			this.timer.start();
			this.life--;
		}
	}
	
	private void checkStatus() {
		this.timer.stop();
		if (this.timer.getDuration() >= 3000)
			this.isUndefeatable = false;
		else
			this.isUndefeatable = true;
	}
	
	public void addScore() {
		this.score += 50;
	}
	
	public int getScore() {
		return this.score;
	}
	
	// determine whether the hero collides with any monster
	public void collideWithMonsters(ArrayList<Monster> monsters) {
		for (Monster m : monsters) {
			double xH = this.centerPoint.getX();
			double yH = this.centerPoint.getY();
			double xM = m.centerPoint.getX();
			double yM = m.centerPoint.getY();
			if (xH >= xM - Level.ITEM_SIZE && xH <= xM + Level.ITEM_SIZE) {
				if (yH >= yM - Level.ITEM_SIZE && yH <= yM + Level.ITEM_SIZE) {
					if(m.isTrapped) {
						this.level.addMonsterToremove(m);;
						this.level.charactersRemove.add(m);
						Point2D.Double point= new Point2D.Double(xH+50,yH+50);
						this.level.fruits.add(new Fruit(this.level,point));
					} else {
						this.die();
					}
				}	
			}
		}
	}
	
	// overall function that updates the hero's position and collision
	@Override 
	public void updatePosition() {
		this.checkStatus();
		this.fall();
		this.shift();
		ArrayList<Rock> rocks = level.getRocks();
		for (Rock r : rocks) {
			if(this.onPlatform(r))
				break;
		}
		this.collideWithMonsters(this.level.getMonsters());
	}

	@Override
	public void addToLevel() {
		// do nothing
		
	}
}
